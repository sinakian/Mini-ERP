package dev.ordy.erp.sales.orderItem;

import dev.ordy.erp.finance.itemPrice.ItemPrice;
import dev.ordy.erp.finance.tax.Tax;
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
        return orderItemService.getOrderItemById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
    }

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
     * Update the tax of an order item
     */
    @PatchMapping("/{id}/tax")
    public OrderItem updateTax(@PathVariable Long id, @RequestBody Map<String, Object> taxUpdate) {
        OrderItem existingItem = orderItemService.getOrderItemById(id);

        // Create a new item with only tax-related fields updated
        OrderItem updateItem = new OrderItem.Builder(existingItem)
                .withTax(taxUpdate.containsKey("taxId") ?
                        new Tax(null, null, null, null, null, null, null) {{
                            setId(((Number)taxUpdate.get("taxId")).longValue());
                        }} :
                        null)
                .withCustomTaxRate(taxUpdate.containsKey("customTaxRate") ?
                        ((Number)taxUpdate.get("customTaxRate")).doubleValue() :
                        null)
                .build();

        return orderItemService.updateOrderItem(id, updateItem);
    }

    /**
     * Update the price of an order item
     */
    @PatchMapping("/{id}/price")
    public OrderItem updatePrice(@PathVariable Long id, @RequestBody Map<String, Object> priceUpdate) {
        OrderItem existingItem = orderItemService.getOrderItemById(id);

        // Create a new item with only price-related fields updated
        OrderItem updateItem = new OrderItem.Builder(existingItem)
                .withItemPrice(priceUpdate.containsKey("itemPriceId") ?
                        new ItemPrice(null, null, null, null) {{
                            setId(((Number)priceUpdate.get("itemPriceId")).longValue());
                        }} :
                        null)
                .withCustomPricePerUnit(priceUpdate.containsKey("customPricePerUnit") ?
                        ((Number)priceUpdate.get("customPricePerUnit")).doubleValue() :
                        null)
                .build();

        return orderItemService.updateOrderItem(id, updateItem);
    }

    /**
     * Update an entire order item
     */
    @PutMapping("/{id}")
    public OrderItem updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        return orderItemService.updateOrderItem(id, orderItem);
    }
}