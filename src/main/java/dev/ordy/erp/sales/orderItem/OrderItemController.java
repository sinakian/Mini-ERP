package dev.ordy.erp.sales.orderItem;

import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public OrderItem one(@PathVariable Long id) {
        return orderItemService.getOrderItemById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public void deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
    }
}
