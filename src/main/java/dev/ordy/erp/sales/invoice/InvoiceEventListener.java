package dev.ordy.erp.sales.invoice;


import dev.ordy.erp.business.account.Account;
import dev.ordy.erp.business.account.AccountCreateEvent;
import dev.ordy.erp.common.Currency;
import dev.ordy.erp.finance.accountBalance.BalanceStatus;
import dev.ordy.erp.finance.financialReceipt.FinancialReceipt;
import dev.ordy.erp.finance.financialReceipt.FinancialReceiptService;
import dev.ordy.erp.finance.financialReceipt.enums.FinancialReceiptReferenceType;
import dev.ordy.erp.finance.financialReceipt.enums.FinancialReceiptType;
import dev.ordy.erp.finance.financialTransaction.FinancialTransactionService;
import dev.ordy.erp.finance.financialTransaction.enums.FinancialTransactionType;
import dev.ordy.erp.finance.financialTransaction.enums.TransactionReferenceType;
import dev.ordy.erp.inventory.inventoryRequest.InventoryRequest;
import dev.ordy.erp.inventory.inventoryRequest.InventoryRequestService;
import dev.ordy.erp.inventory.inventoryRequestItem.InventoryRequestItemService;
import dev.ordy.erp.sales.order.OrderService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InvoiceEventListener implements ApplicationListener<InvoiceCreateEvent> {

    private final OrderService orderService;
    private final InventoryRequestService inventoryRequestService;
    private final FinancialReceiptService financialReceiptService;
    private final FinancialTransactionService financialTransactionService;


    public InvoiceEventListener(OrderService orderService, InventoryRequestService inventoryRequestService, InventoryRequestItemService inventoryRequestItemService, FinancialReceiptService financialReceiptService, FinancialTransactionService financialTransactionService) {
        this.orderService = orderService;
        this.inventoryRequestService = inventoryRequestService;
        this.financialReceiptService = financialReceiptService;
        this.financialTransactionService = financialTransactionService;
    }
    @Override
    @Transactional
    public void onApplicationEvent(InvoiceCreateEvent event) {

        Invoice invoice=event.getInvoice();
        //update order status to invoice
        Long orderId = invoice.getOrder().getId();
        orderService.changeStatusToInvoice(orderId);

        //create an inventory Request
        InventoryRequest inventoryRequest= new InventoryRequest(
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


        //Create financial Receipt for Customer
        FinancialReceipt financialReceipt=financialReceiptService.createFinancialReceipt(
                invoice.getCustomer(),
                invoice.getTotal(),
                FinancialReceiptReferenceType.INVOICE,
                FinancialReceiptType.DEBIT,
                invoice.getId(),
                invoice.getCurrency()
        );

        //create Debit Transaction for Customer
        financialTransactionService.createTransaction(
                FinancialTransactionType.DEBT,
                TransactionReferenceType.DEBT_RECEIPT,
                financialReceipt.getCurrency(),
                financialReceipt.getAccount(),
                financialReceipt,
                financialReceipt.getAmount(),
                financialReceipt.getId()
        );


        // Log a message
        System.out.println("Invoice Create Event Done");
    }

}
