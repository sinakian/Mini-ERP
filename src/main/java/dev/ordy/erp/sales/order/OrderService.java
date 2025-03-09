package dev.ordy.erp.sales.order;


import dev.ordy.erp.sales.orderItem.OrderItem;
import dev.ordy.erp.sales.orderItem.OrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final OrderItemService  orderItemService;

    public OrderService(OrderRepository orderRepository,
                        ApplicationEventPublisher eventPublisher,
                        OrderItemService orderItemService
                        ) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
        this.orderItemService = orderItemService;
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Transactional
    public Order createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        eventPublisher.publishEvent(new OrderCreateEvent(this, savedOrder));
        return savedOrder;
    }

    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }


    public void confirmOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        order.setConfirmationState(ConfirmationState.CONFIRMED);
        orderRepository.save(order);
        eventPublisher.publishEvent(new OrderConfirmEvent(this, order));
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        order.setConfirmationState(ConfirmationState.CANCELLED);
        orderRepository.save(order);
    }

    @Transactional
    public Order recalculateOrderTotals(Long orderId) {
        // Get the order
        Order order = getOrderById(orderId);

        // Get all order items for this order
        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderId(orderId);

        // Calculate total item price (sum of all item net prices)
        double totalItemPrice = orderItems.stream()
                .mapToDouble(OrderItem::getTotalNetPrice)
                .sum();

        // Set the calculated values
        order.setTotalItemPrice(totalItemPrice);

        // Calculate total after discounts
        double discountAmount = totalItemPrice * (order.getDiscountInPercent() / 100) + order.getDiscountInCurrency();
        double totalAfterDiscounts = totalItemPrice - discountAmount + order.getTotalLogisticPrice();

        // Set the final total
        order.setTotal(totalAfterDiscounts);

        // Save and return the updated order
        return orderRepository.save(order);
    }

}
