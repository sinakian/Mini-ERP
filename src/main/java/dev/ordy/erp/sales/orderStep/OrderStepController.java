package dev.ordy.erp.sales.orderStep;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-steps")
public class OrderStepController {

    private final OrderStepService orderStepService;

    public OrderStepController(OrderStepService orderStepService) {
        this.orderStepService = orderStepService;
    }

    @GetMapping
    public List<OrderStep> all() {
        return orderStepService.getAllOrderSteps();
    }

    @PostMapping
    public OrderStep newOrderStep(@RequestBody OrderStep newOrderStep) {
        return orderStepService.createOrderStep(newOrderStep);
    }

    @GetMapping("/order/{orderId}")
    public List<OrderStep> getOrderStepsByOrderId(@PathVariable Long orderId) {
        return orderStepService.getOrderStepsByOrderId(orderId);
    }

    @GetMapping("/{id}")
    public OrderStep one(@PathVariable Long id) {
        return orderStepService.getOrderStepById(id)
                .orElseThrow(() -> new OrderStepNotFoundException(id));
    }

    // Controller
    @PatchMapping("/{stepId}/complete")
    public ResponseEntity<OrderStep> markStepAsCompleted(@PathVariable Long stepId) {
        OrderStep updatedStep = orderStepService.markStepAsCompleted(stepId);
        return ResponseEntity.ok(updatedStep);
    }

    @PatchMapping("/{stepId}/incomplete")
    public ResponseEntity<OrderStep> markStepAsIncomplete(@PathVariable Long stepId) {
        OrderStep updatedStep = orderStepService.markStepAsIncomplete(stepId);
        return ResponseEntity.ok(updatedStep);
    }


    @DeleteMapping("/{id}")
    public void deleteOrderItem(@PathVariable Long id) {
        orderStepService.deleteOrderStep(id);
    }
}
