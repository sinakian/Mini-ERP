package dev.ordy.erp.sales.order;

import dev.ordy.erp.business.business_settings.BusinessSettingsService;
import dev.ordy.erp.sales.orderStep.OrderStep;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import dev.ordy.erp.sales.orderStep.OrderStepService;

import java.util.List;


@Component
public class OrderEventListener implements ApplicationListener<OrderCreateEvent> {

    private final OrderStepService orderStepService;
    private final BusinessSettingsService businessSettingsService;

    public OrderEventListener(OrderService orderService,
                              OrderStepService orderStepService,
                              BusinessSettingsService businessSettingsService
    ) {
        this.orderStepService = orderStepService;
        this.businessSettingsService = businessSettingsService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(OrderCreateEvent event) {

        Order order = event.getOrder();
        try {
            // update order status to invoice
            Long orderId = order.getId();
            Long businessId = order.getBusiness().getId();
            Long stepSetId = businessSettingsService.getDefaultSettings(businessId).getDefaultStepSet().getId();

            // Create Order Steps
            List<OrderStep> orderSteps = orderStepService.createOrderSteps(businessId,orderId,stepSetId);


            // Log a message
            System.out.println("Order Create Event Done");
        } catch (Exception e) {
            // Log or rethrow the exception to trigger rollback
            System.err.println("Error occurred while processing Order creation: " + e.getMessage());
            throw e;  // This will cause the transaction to roll back
        }
    }
}
