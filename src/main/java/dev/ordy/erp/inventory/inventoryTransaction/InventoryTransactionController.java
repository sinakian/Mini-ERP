package dev.ordy.erp.inventory.inventoryTransaction;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory-transactions")
class InventoryTransactionController {

    private final InventoryTransactionRepository repository;

    InventoryTransactionController(InventoryTransactionRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    List<InventoryTransaction> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping
    InventoryTransaction newBusiness(@RequestBody InventoryTransaction newInventoryTransaction) {
        return repository.save(newInventoryTransaction);
    }

    // Single item

    @GetMapping("/{id}")
    InventoryTransaction one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new InventoryTransactionNotFoundException(id));
    }

    @PutMapping("/{id}")
    InventoryTransaction replaceBusiness(@RequestBody InventoryTransaction newInventoryTransaction, @PathVariable Long id) {

        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newInventoryTransaction.getName());
                    employee.setRole(newInventoryTransaction.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newInventoryTransaction.setId(id);
                    return repository.save(newInventoryTransaction);
                });
    }

    @DeleteMapping("/{id}")
    void deleteBusiness(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
