package dev.ordy.erp.sales.orderItem;

import dev.ordy.erp.sales.order.OrderItemChangeEvent;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dev.ordy.erp.sales.order.Order;
import dev.ordy.erp.sales.order.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;

    public OrderItemService(OrderItemRepository orderItemRepository,
                            OrderRepository orderRepository,
                            ApplicationEventPublisher eventPublisher
                            ) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(readOnly = true)
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<OrderItem> getOrderItemsByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }

    @Transactional(readOnly = true)
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        // Find the order first
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Return items associated with this order
        return orderItemRepository.findByOrder(order);
    }

    @Transactional
    public OrderItem createOrderItem(OrderItem orderItem) {
        OrderItem savedItem = orderItemRepository.save(orderItem);
        eventPublisher.publishEvent(new OrderItemChangeEvent(savedItem.getOrder().getId()));
        return savedItem;
    }

    @Transactional
    public void deleteOrderItem(Long id) {
        OrderItem itemToDelete = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(id));
        Long orderId = itemToDelete.getOrder().getId();
        orderItemRepository.deleteById(id);
        eventPublisher.publishEvent(new OrderItemChangeEvent(orderId));
    }

    @Transactional
    public List<OrderItem> createOrderItems(List<OrderItem> orderItems) {
        List<OrderItem> savedItems = orderItemRepository.saveAll(orderItems);

        // Group by order ID and publish events for each affected order
        savedItems.stream()
                .map(item -> item.getOrder().getId())
                .distinct()
                .forEach(orderId -> eventPublisher.publishEvent(new OrderItemChangeEvent(orderId)));

        return savedItems;
    }


    /**
     * Updates only the quantity of an order item and recalculates totals
     */
    @Transactional
    public OrderItem updateOrderItemQuantity(Long id, double newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(id));

        // Update quantity
        orderItem = updateQuantityAndRecalculateTotals(orderItem, newQuantity);

        // Save and return updated item
        OrderItem savedItem = orderItemRepository.save(orderItem);

        // Publish event after update
        eventPublisher.publishEvent(new OrderItemChangeEvent(savedItem.getOrder().getId()));

        return savedItem;
    }

    /**
     * Update an entire order item
     */
    @Transactional
    public OrderItem updateOrderItem(Long id, OrderItem updatedOrderItem) {
        OrderItem existingOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(id));

        existingOrderItem = updateOrderItemFields(existingOrderItem, updatedOrderItem);

        OrderItem savedItem = orderItemRepository.save(existingOrderItem);

        // Publish event after update
        eventPublisher.publishEvent(new OrderItemChangeEvent(savedItem.getOrder().getId()));

        return savedItem;
    }

    private OrderItem updateQuantityAndRecalculateTotals(OrderItem orderItem, double newQuantity) {
        // Update the quantity
        orderItem.setQuantity(newQuantity);

        // Use the built-in method to recalculate totals
        orderItem.recalculateTotals();

        return orderItem;
    }


    /**
     * Helper method to calculate tax amount
     */
    private double calculateTax(double grossPrice, double taxRate) {
        return grossPrice * taxRate;
    }

    /**
     * Helper method to calculate net price
     */
    private double calculateNetPrice(double grossPrice, double discountCurrency,
                                     double discountPercent, double taxRate) {
        double afterDiscount = grossPrice - discountCurrency - (grossPrice * (discountPercent / 100));
        return afterDiscount + (afterDiscount * taxRate);
    }

    /**
     * Helper method to update order item fields
     */
    private OrderItem updateOrderItemFields(OrderItem existingItem, OrderItem updatedItem) {
        return new OrderItem.Builder(existingItem)
                .withItem(updatedItem.getItem() != null ? updatedItem.getItem() : existingItem.getItem())
                .withQuantity(updatedItem.getQuantity())
                .withItemPrice(updatedItem.getItemPrice() != null ? updatedItem.getItemPrice() : existingItem.getItemPrice())
                .withPricePerUnit(updatedItem.getPricePerUnit())
                .withUnit(updatedItem.getUnit() != null ? updatedItem.getUnit() : existingItem.getUnit())
                .withCurrency(updatedItem.getCurrency() != null ? updatedItem.getCurrency() : existingItem.getCurrency())
                .withTotalGrossPrice(updatedItem.getTotalGrossPrice())
                .withDiscountCurrency(updatedItem.getDiscountCurrency())
                .withDiscountPercent(updatedItem.getDiscountPercent())
                .withTax(updatedItem.getTax())
                .withTotalNetPrice(updatedItem.getTotalNetPrice())
                .build();
    }
}