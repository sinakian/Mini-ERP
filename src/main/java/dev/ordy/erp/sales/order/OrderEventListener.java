package dev.ordy.erp.sales.order;

import dev.ordy.erp.business.business_settings.BusinessSettingsService;
import dev.ordy.erp.common.FinancialStatus;
import dev.ordy.erp.finance.financialReceipt.FinancialReceipt;
import dev.ordy.erp.finance.financialReceipt.FinancialReceiptReferenceType;
import dev.ordy.erp.finance.financialReceipt.TransactionStatus;
import dev.ordy.erp.inventory.inventoryRequest.InventoryRequest;
import dev.ordy.erp.inventory.inventoryRequestItem.InventoryRequestItem;
import dev.ordy.erp.sales.orderItem.OrderItem;
import dev.ordy.erp.sales.orderStep.OrderStep;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import dev.ordy.erp.sales.orderStep.OrderStepService;
import dev.ordy.erp.inventory.inventoryRequest.InventoryRequestService;
import dev.ordy.erp.sales.orderItem.OrderItemService;
import dev.ordy.erp.inventory.inventoryRequestItem.InventoryRequestItemService;
import dev.ordy.erp.finance.financialReceipt.FinancialReceiptService;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class OrderEventListener {
    private final OrderService orderService;
    private final OrderStepService orderStepService;
    private final BusinessSettingsService businessSettingsService;
    private final InventoryRequestService inventoryRequestService;
    private final OrderItemService orderItemService;
    private final InventoryRequestItemService inventoryRequestItemService;
    private final FinancialReceiptService financialReceiptService;

    public OrderEventListener(OrderService orderService,
                              OrderStepService orderStepService,
                              BusinessSettingsService businessSettingsService,
                              InventoryRequestService inventoryRequestService,
                              OrderItemService orderItemService,
                              InventoryRequestItemService inventoryRequestItemService,
                              FinancialReceiptService financialReceiptService
    ) {
        this.orderService = orderService;
        this.orderStepService = orderStepService;
        this.businessSettingsService = businessSettingsService;
        this.inventoryRequestService = inventoryRequestService;
        this.orderItemService = orderItemService;
        this.inventoryRequestItemService=inventoryRequestItemService;
        this.financialReceiptService=financialReceiptService;
    }

    @EventListener
    @Transactional
    public void handleOrderCreateEvent(OrderCreateEvent event) {

        Order order = event.getOrder();
        try {
            // update order status to invoice
            Long orderId = order.getId();
            Long businessId = order.getBusiness().getId();
            Long stepSetId = businessSettingsService.getDefaultSettings(businessId).getDefaultStepSet().getId();

            // Create Order Steps
            List<OrderStep> orderSteps = orderStepService.createOrderSteps(businessId, orderId, stepSetId);

            // Log a message
            System.out.println("Order Create Event Done");
        } catch (Exception e) {
            // Log or rethrow the exception to trigger rollback
            System.err.println("Error occurred while processing Order creation: " + e.getMessage());
            throw e;  // This will cause the transaction to roll back
        }
    }


    @EventListener
    @Transactional
    public void handleOrderConfirmEvent(OrderConfirmEvent event) {
        Order order = event.getOrder();
        try {
            System.out.println("Order Confirmed: " + order.getId());
            // Add future functionality here
        } catch (Exception e) {
            System.err.println("Error occurred while processing Order confirmation: " + e.getMessage());
            throw e;
        }

        Long businessId = order.getBusiness().getId();

        try {
            // update order status to invoice
            Long orderId = order.getId();


            // create an inventory Request
            InventoryRequest inventoryRequest = new InventoryRequest(
                    order.getBusiness(),
                    businessSettingsService.getDefaultSettings(businessId).getDefaultProductInventory(),
                    0.0,
                    null,
                    null,
                    InventoryRequest.ReferenceType.ORDER,
                    order.getId(),
                    InventoryRequest.Status.PENDING
            );
            inventoryRequestService.createInventoryRequest(inventoryRequest);

            List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderId(orderId);

            // Create Inventory Request Items
            List<InventoryRequestItem> inventoryRequestItems = orderItems.stream().map(invoiceItem ->
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
                    order.getCustomer(),
                    order.getTotal(),
                    FinancialReceiptReferenceType.ORDER,
                    FinancialStatus.DEBIT,
                    order.getId(),
                    order.getCurrency(),
                    TransactionStatus.NOT_PROCESSED
            );


            // Log a message
            System.out.println("Order Confiration Event Done");
        } catch (Exception e) {
            // Log or rethrow the exception to trigger rollback
            System.err.println("Error occurred while processing Order Confirmation: " + e.getMessage());
            throw e;  // This will cause the transaction to roll back
        }





    }

    @EventListener
    @Transactional
    public void handleOrderItemChangeEvent(OrderItemChangeEvent event) {
        orderService.recalculateOrderTotals(event.getOrderId());
    }



}

