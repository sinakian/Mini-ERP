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
     * Update with a custom tax or remove the current tax
     * Note: If no tax is specified, the system will apply the business default tax
     */
    @PatchMapping("/{id}/tax")
    public OrderItem updateCustomTax(@PathVariable Long id, @RequestBody Map<String, Object> taxUpdate) {
        OrderItem existingItem = orderItemService.getOrderItemById(id);

        // Create a new item with only tax-related fields updated
        OrderItem.Builder builder = new OrderItem.Builder(existingItem);

        // Handle specific tax ID if provided
        if (taxUpdate.containsKey("taxId")) {
            Long taxId = ((Number)taxUpdate.get("taxId")).longValue();
            if (taxId > 0) {
                // Set specific tax
                builder.withTax(new Tax(null, null, null, null, null, null, null) {{
                    setId(taxId);
                }});
            } else {
                // Null means use default tax
                builder.withTax(null);
            }
        }

        // Handle custom tax rate if provided
        if (taxUpdate.containsKey("customTaxRate")) {
            builder.withCustomTaxRate(((Number)taxUpdate.get("customTaxRate")).doubleValue());
        }

        // Handle explicit removal of custom tax rate
        if (taxUpdate.containsKey("removeCustomTaxRate") &&
                Boolean.TRUE.equals(taxUpdate.get("removeCustomTaxRate"))) {
            builder.withCustomTaxRate(null);
        }

        return orderItemService.updateOrderItem(id, builder.build());
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

    @PatchMapping("/{id}/discount")
    public ResponseEntity<OrderItem> updateOrderItemDiscount(
            @PathVariable Long id,
            @RequestBody Map<String, Object> discountData) {

        OrderItem orderItem = orderItemService.getOrderItemById(id);

        if (discountData.containsKey("discountPercent")) {
            Double discountPercent = Double.valueOf(discountData.get("discountPercent").toString());
            orderItem.setDiscountPercent(discountPercent);
        }

        if (discountData.containsKey("discountCurrency")) {
            Double discountCurrency = Double.valueOf(discountData.get("discountCurrency").toString());
            orderItem.setDiscountCurrency(discountCurrency);
        }

        // Recalculate totals
        orderItem.recalculateTotals();

        OrderItem updatedItem = orderItemService.updateOrderItem(id, orderItem);
        return ResponseEntity.ok(updatedItem);
    }
}