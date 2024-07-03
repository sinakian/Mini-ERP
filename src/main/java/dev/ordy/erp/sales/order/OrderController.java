package dev.ordy.erp.sales.order;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> all() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public Order newOrder(@RequestBody Order newOrder) {
        return orderService.createOrder(newOrder);
    }

    @GetMapping("/{id}")
    public Order one(@PathVariable Long id) {
        return orderService.getOrderById(id);
//                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
