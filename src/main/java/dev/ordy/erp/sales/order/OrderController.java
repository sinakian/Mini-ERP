package dev.ordy.erp.sales.order;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
class OrderController {

    private final OrderRepository repository;

    OrderController(OrderRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    List<Order> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping
    Order newOrder(@RequestBody Order newOrder) {
        return repository.save(newOrder);
    }

    // Single item

    @GetMapping("/{id}")
    Order one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

//    @PutMapping("/{id}")
//    Order replaceOrder(@RequestBody Order newOrder, @PathVariable Long id) {
//
//        return repository.findById(id)
//                .map(order -> {
//                    order.setName(newOrder.getName());
//                    order.setRole(newOrder.getRole());
//                    return repository.save(order);
//                })
//                .orElseGet(() -> {
//                    newOrder.setId(id);
//                    return repository.save(newOrder);
//                });
//    }

    @DeleteMapping("/{id}")
    void deleteOrder(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
