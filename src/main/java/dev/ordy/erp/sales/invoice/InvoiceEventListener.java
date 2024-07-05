package dev.ordy.erp.sales.invoice;

import dev.ordy.erp.finance.financialReceipt.FinancialReceipt;
import dev.ordy.erp.finance.financialReceipt.FinancialReceiptService;
import dev.ordy.erp.finance.financialReceipt.enums.FinancialReceiptReferenceType;
import dev.ordy.erp.finance.financialReceipt.enums.FinancialReceiptType;
import dev.ordy.erp.finance.financialTransaction.FinancialTransactionService;
import dev.ordy.erp.finance.financialTransaction.enums.FinancialTransactionType;
import dev.ordy.erp.finance.financialTransaction.enums.TransactionReferenceType;
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

    public InvoiceEventListener(OrderService orderService, InventoryRequestService inventoryRequestService,
                                InventoryRequestItemService inventoryRequestItemService, FinancialReceiptService financialReceiptService,
                                FinancialTransactionService financialTransactionService, InvoiceItemService invoiceItemService) {
        this.orderService = orderService;
        this.inventoryRequestService = inventoryRequestService;
        this.inventoryRequestItemService = inventoryRequestItemService;
        this.financialReceiptService = financialReceiptService;
        this.financialTransactionService = financialTransactionService;
        this.invoiceItemService = invoiceItemService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(InvoiceCreateEvent event) {

        Invoice invoice = event.getInvoice();
        try {
            // update order status to invoice
            Long orderId = invoice.getOrder().getId();
            orderService.changeStatusToInvoice(orderId);

            // Create Invoice Item
            List<InvoiceItem> invoiceItems = invoiceItemService.createInvoiceItemsByOrder(invoice.getOrder(), invoice);

            // create an inventory Request
            InventoryRequest inventoryRequest = new InventoryRequest(
                    invoice.getBusiness(),
                    invoice.getBusiness().getDefaultProductInventory(),
                    0.0,
                    null,
                    null,
                    InventoryRequest.ReferenceType.INVOICE,
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
                    FinancialReceiptReferenceType.INVOICE,
                    FinancialReceiptType.DEBIT,
                    invoice.getId(),
                    invoice.getCurrency()
            );

            // create Debit Transaction for Customer
            financialTransactionService.createTransaction(
                    FinancialTransactionType.DEBIT,
                    TransactionReferenceType.DEBT_RECEIPT,
                    financialReceipt.getCurrency(),
                    financialReceipt.getAccount(),
                    financialReceipt,
                    financialReceipt.getAmount(),
                    financialReceipt.getId()
            );

            // Log a message
            System.out.println("Invoice Create Event Done");
        } catch (Exception e) {
            // Log or rethrow the exception to trigger rollback
            System.err.println("Error occurred while processing invoice creation: " + e.getMessage());
            throw e;  // This will cause the transaction to roll back
        }
    }
}
