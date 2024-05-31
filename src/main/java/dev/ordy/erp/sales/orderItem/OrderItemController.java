package dev.ordy.erp.sales.orderItem;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-items")
class OrderItemController {

    private final OrderItemRepository repository;

    OrderItemController(OrderItemRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    List<OrderItem> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping
    OrderItem newOrderItem(@RequestBody OrderItem newOrderItem) {
        return repository.save(newOrderItem);
    }

    // Single item

    @GetMapping("/{id}")
    OrderItem one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(id));
    }

//    @PutMapping("/{id}")
//    OrderItem replaceOrderItem(@RequestBody OrderItem newOrderItem, @PathVariable Long id) {
//
//        return repository.findById(id)
//                .map(orderItem -> {
//                    orderItem.setName(newOrderItem.getName());
//                    orderItem.setRole(newOrderItem.getRole());
//                    return repository.save(orderItem);
//                })
//                .orElseGet(() -> {
//                    newOrderItem.setId(id);
//                    return repository.save(newOrderItem);
//                });
//    }

    @DeleteMapping("/{id}")
    void deleteOrderItem(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
