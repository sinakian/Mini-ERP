package dev.ordy.erp.sales.orderItem;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public List<OrderItem> all() {
        return orderItemService.getAllOrderItems();
    }

    @PostMapping
    public OrderItem newOrderItem(@RequestBody OrderItem newOrderItem) {
        return orderItemService.createOrderItem(newOrderItem);
    }

    @PostMapping("/bulk")
    public List<OrderItem> createOrderItems(@RequestBody List<OrderItem> orderItems) {
        return orderItemService.createOrderItems(orderItems);
    }

    @GetMapping("/{id}")
    public OrderItem one(@PathVariable Long id) {
        return orderItemService.getOrderItemById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public void deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
    }

    // New endpoints to support frontend functionality

    /**
     * Get all order items for a specific order
     */
    @GetMapping("/order/{orderId}")
    public List<OrderItem> getByOrderId(@PathVariable Long orderId) {
        return orderItemService.getOrderItemsByOrderId(orderId);
    }

    /**
     * Update the quantity of an order item
     */
    @PatchMapping("/{id}/quantity")
    public OrderItem updateQuantity(@PathVariable Long id, @RequestBody Map<String, Double> quantityUpdate) {
        Double newQuantity = quantityUpdate.get("quantity");
        if (newQuantity == null) {
            throw new IllegalArgumentException("Quantity value is required");
        }
        return orderItemService.updateOrderItemQuantity(id, newQuantity);
    }

    /**
     * Update an entire order item
     */
    @PutMapping("/{id}")
    public OrderItem updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        return orderItemService.updateOrderItem(id, orderItem);
    }
}