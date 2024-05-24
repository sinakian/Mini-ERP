package dev.ordy.erp.inventory.inventory;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventories")
class InventoryController {

    private final InventoryRepository repository;

    InventoryController(InventoryRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    List<Inventory> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping
    Inventory newBusiness(@RequestBody Inventory newInventory) {
        return repository.save(newInventory);
    }

    // Single item

    @GetMapping("/{id}")
    Inventory one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException(id));
    }

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
    void deleteBusiness(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
