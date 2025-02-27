package dev.ordy.erp.sales.orderItem;

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

    public OrderItemService(OrderItemRepository orderItemRepository, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<OrderItem> getOrderItemById(Long id) {
        return orderItemRepository.findById(id);
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
        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }

    @Transactional
    public List<OrderItem> createOrderItems(List<OrderItem> orderItems) {
        return orderItemRepository.saveAll(orderItems);
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
        return orderItemRepository.save(orderItem);
    }

    /**
     * Update an entire order item
     */
    @Transactional
    public OrderItem updateOrderItem(Long id, OrderItem updatedOrderItem) {
        OrderItem existingOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(id));

        // Update the mutable fields
        // Note: In a real application, you might want to be more selective about what can be updated
        // Here we're assuming all fields can be updated

        existingOrderItem = updateOrderItemFields(existingOrderItem, updatedOrderItem);

        return orderItemRepository.save(existingOrderItem);
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