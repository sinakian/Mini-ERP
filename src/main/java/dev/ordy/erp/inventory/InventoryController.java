package dev.ordy.erp.inventory;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class InventoryController {

    private final InventoryRepository repository;

    InventoryController(InventoryRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/inventories")
    List<Inventory> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/inventories")
    Inventory newBusiness(@RequestBody Inventory newInventory) {
        return repository.save(newInventory);
    }

    // Single item

    @GetMapping("/inventories/{id}")
    Inventory one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException(id));
    }

    @PutMapping("/inventories/{id}")
    Inventory replaceBusiness(@RequestBody Inventory newInventory, @PathVariable Long id) {

        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newInventory.getName());
                    employee.setRole(newInventory.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newInventory.setId(id);
                    return repository.save(newInventory);
                });
    }

    @DeleteMapping("/inventories/{id}")
    void deleteBusiness(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
