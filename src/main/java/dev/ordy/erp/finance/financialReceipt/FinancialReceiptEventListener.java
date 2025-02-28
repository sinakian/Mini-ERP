package dev.ordy.erp.finance.financialReceipt;

import dev.ordy.erp.business.business_settings.BusinessSettingsService;
import dev.ordy.erp.common.FinancialStatus;
import dev.ordy.erp.finance.financialTransaction.FinancialTransactionService;
import dev.ordy.erp.inventory.inventoryRequest.InventoryRequest;
import dev.ordy.erp.inventory.inventoryRequest.InventoryRequestService;
import dev.ordy.erp.inventory.inventoryRequestItem.InventoryRequestItem;
import dev.ordy.erp.inventory.inventoryRequestItem.InventoryRequestItemService;
import dev.ordy.erp.sales.order.Order;
import dev.ordy.erp.sales.order.OrderConfirmEvent;
import dev.ordy.erp.sales.order.OrderCreateEvent;
import dev.ordy.erp.sales.order.OrderService;
import dev.ordy.erp.sales.orderItem.OrderItem;
import dev.ordy.erp.sales.orderItem.OrderItemService;
import dev.ordy.erp.sales.orderStep.OrderStep;
import dev.ordy.erp.sales.orderStep.OrderStepService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class FinancialReceiptEventListener {

    private final OrderStepService orderStepService;
    private final BusinessSettingsService businessSettingsService;
    private final InventoryRequestService inventoryRequestService;
    private final OrderItemService orderItemService;
    private final InventoryRequestItemService inventoryRequestItemService;
    private final FinancialReceiptService financialReceiptService;
    private final FinancialTransactionService financialTransactionService;

    public FinancialReceiptEventListener(OrderService orderService,
                                         OrderStepService orderStepService,
                                         BusinessSettingsService businessSettingsService,
                                         InventoryRequestService inventoryRequestService,
                                         OrderItemService orderItemService,
                                         InventoryRequestItemService inventoryRequestItemService,
                                         FinancialReceiptService financialReceiptService,
                                         FinancialTransactionService financialTransactionService
    ) {
        this.orderStepService = orderStepService;
        this.businessSettingsService = businessSettingsService;
        this.inventoryRequestService = inventoryRequestService;
        this.orderItemService = orderItemService;
        this.inventoryRequestItemService=inventoryRequestItemService;
        this.financialReceiptService=financialReceiptService;
        this.financialTransactionService=financialTransactionService;
    }

    @EventListener
    @Transactional
    public void handleFinancialReceiptConfirmEvent(FinancialReceiptConfirmEvent event) {
        FinancialReceipt financialReceipt = event.getReceipt();
        try {
            System.out.println("financial receipt confirmed: " + financialReceipt.getId());
            // Add future functionality here
        } catch (Exception e) {
            System.err.println("Error occurred while processing financial receipt confirmation: " + e.getMessage());
            throw e;
        }

        try {
            financialTransactionService.createTransaction(financialReceipt.getId());
           // Log a message
            System.out.println("Order Confiration Event Done");
        } catch (Exception e) {
            // Log or rethrow the exception to trigger rollback
            System.err.println("Error occurred while processing Order Confirmation: " + e.getMessage());
            throw e;  // This will cause the transaction to roll back
        }





    }



}

