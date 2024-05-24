package dev.ordy.erp.inventory;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class InventoryItemController {

    private final InventoryItemRepository repository;

    InventoryItemController(InventoryItemRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/inventoryitems")
    List<InventoryItem> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/inventoryitems")
    InventoryItem newBusiness(@RequestBody InventoryItem newInventoryItem) {
        return repository.save(newInventoryItem);
    }

    // Single item

    @GetMapping("/inventoryitems/{id}")
    InventoryItem one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new InventoryItemNotFoundException(id));
    }

    @PutMapping("/inventoryitems/{id}")
    InventoryItem replaceBusiness(@RequestBody InventoryItem newInventoryItem, @PathVariable Long id) {

        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newInventoryItem.getName());
                    employee.setRole(newInventoryItem.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newInventoryItem.setId(id);
                    return repository.save(newInventoryItem);
                });
    }

    @DeleteMapping("/inventoryitems/{id}")
    void deleteBusiness(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
