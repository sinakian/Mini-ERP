package dev.ordy.erp.sales.invoice;

import dev.ordy.erp.business.business_settings.BusinessSettingsService;
import dev.ordy.erp.common.FinancialStatus;
import dev.ordy.erp.finance.financialReceipt.FinancialReceipt;
import dev.ordy.erp.finance.financialReceipt.FinancialReceiptService;
import dev.ordy.erp.finance.financialReceipt.FinancialReceiptReferenceType;
import dev.ordy.erp.finance.financialReceipt.TransactionStatus;
import dev.ordy.erp.finance.financialTransaction.FinancialTransactionService;
import dev.ordy.erp.inventory.inventoryRequest.InventoryRequest;
import dev.ordy.erp.inventory.inventoryRequest.InventoryRequestService;
import dev.ordy.erp.inventory.inventoryRequestItem.InventoryRequestItem;
import dev.ordy.erp.inventory.inventoryRequestItem.InventoryRequestItemService;
import dev.ordy.erp.sales.invoiceItem.InvoiceItem;
import dev.ordy.erp.sales.invoiceItem.InvoiceItemService;
import dev.ordy.erp.sales.order.OrderService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceEventListener implements ApplicationListener<InvoiceCreateEvent> {

    private final OrderService orderService;
    private final InventoryRequestService inventoryRequestService;
    private final InventoryRequestItemService inventoryRequestItemService;
    private final FinancialReceiptService financialReceiptService;
    private final FinancialTransactionService financialTransactionService;
    private final InvoiceItemService invoiceItemService;
    private final BusinessSettingsService businessSettingsService;

    public InvoiceEventListener(OrderService orderService,
                                InventoryRequestService inventoryRequestService,
                                InventoryRequestItemService inventoryRequestItemService,
                                FinancialReceiptService financialReceiptService,
                                FinancialTransactionService financialTransactionService,
                                InvoiceItemService invoiceItemService,
                                BusinessSettingsService businessSettingsService
    ) {
        this.orderService = orderService;
        this.inventoryRequestService = inventoryRequestService;
        this.inventoryRequestItemService = inventoryRequestItemService;
        this.financialReceiptService = financialReceiptService;
        this.financialTransactionService = financialTransactionService;
        this.invoiceItemService = invoiceItemService;
        this.businessSettingsService = businessSettingsService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(InvoiceCreateEvent event) {

        Invoice invoice = event.getInvoice();
        Long businessId = invoice.getBusiness().getId();

        try {
            // update order status to invoice
            Long orderId = invoice.getOrder().getId();


            // Create Invoice Item
            List<InvoiceItem> invoiceItems = invoiceItemService.createInvoiceItemsByOrder(invoice.getOrder(), invoice);

            // create an inventory Request
            InventoryRequest inventoryRequest = new InventoryRequest(
                    invoice.getBusiness(),
                    businessSettingsService.getDefaultSettings(businessId).getDefaultProductInventory(),
                    0.0,
                    null,
                    null,
                    InventoryRequest.ReferenceType.ORDER,
                    invoice.getId(),
                    InventoryRequest.Status.PENDING
            );
            inventoryRequestService.createInventoryRequest(inventoryRequest);

            // Create Inventory Request Items
            List<InventoryRequestItem> inventoryRequestItems = invoiceItems.stream().map(invoiceItem ->
                    new InventoryRequestItem(
                            inventoryRequest,
                            invoiceItem.getItem(),
                            invoiceItem.getQuantity(),
                            0.0,
                            0.0,
                            null, // TODO: Set due date
                            null, // TODO: Set delivered date
                            invoiceItem.getUnit(),
                            InventoryRequestItem.Status.PENDING
                    )
            ).collect(Collectors.toList());

            for (InventoryRequestItem inventoryRequestItem : inventoryRequestItems) {
                inventoryRequestItemService.createInventoryRequestItem(inventoryRequestItem);
            }

            // Create financial Receipt for Customer
            FinancialReceipt financialReceipt = financialReceiptService.createFinancialReceipt(
                    invoice.getCustomer(),
                    invoice.getTotal(),
                    FinancialReceiptReferenceType.ORDER,
                    FinancialStatus.DEBIT,
                    invoice.getId(),
                    invoice.getCurrency(),
                    TransactionStatus.NOT_PROCESSED
            );

            // create Debit Transaction for Customer
            financialTransactionService.createTransaction(financialReceipt.getId());

            // Log a message
            System.out.println("Invoice Create Event Done");
        } catch (Exception e) {
            // Log or rethrow the exception to trigger rollback
            System.err.println("Error occurred while processing invoice creation: " + e.getMessage());
            throw e;  // This will cause the transaction to roll back
        }
    }
}
