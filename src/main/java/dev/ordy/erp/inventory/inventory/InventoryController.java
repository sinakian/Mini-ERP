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
    Inventory newInventory(@RequestBody Inventory newInventory) {
        return repository.save(newInventory);
    }

    // Single item

    @GetMapping("/{id}")
    Inventory one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException(id));
    }

    @PutMapping("/{id}")
    Inventory replaceInventory(@RequestBody Inventory newInventory, @PathVariable Long id) {

        return repository.findById(id)
                .map(inventory -> {
                    inventory.setRole(newInventory.getRole());
                    return repository.save(inventory);
                })
                .orElseGet(() -> {
                    newInventory.setId(id);
                    return repository.save(newInventory);
                });
    }

    @DeleteMapping("/{id}")
    void deleteInventory(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
