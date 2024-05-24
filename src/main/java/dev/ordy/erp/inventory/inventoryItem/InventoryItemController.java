package dev.ordy.erp.inventory.inventoryItem;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory-items")
class InventoryItemController {

    private final InventoryItemRepository repository;

    InventoryItemController(InventoryItemRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    List<InventoryItem> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping
    InventoryItem newInventoryItem(@RequestBody InventoryItem newInventoryItem) {
        return repository.save(newInventoryItem);
    }

    // Single item

    @GetMapping("/{id}")
    InventoryItem one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new InventoryItemNotFoundException(id));
    }

    @PutMapping("/{id}")
    InventoryItem replaceInventoryItem(@RequestBody InventoryItem newInventoryItem, @PathVariable Long id) {

        return repository.findById(id)
                .map(inventoryItem -> {
                    inventoryItem.setName(newInventoryItem.getName());
                    inventoryItem.setRole(newInventoryItem.getRole());
                    return repository.save(inventoryItem);
                })
                .orElseGet(() -> {
                    newInventoryItem.setId(id);
                    return repository.save(newInventoryItem);
                });
    }

    @DeleteMapping("/{id}")
    void deleteInventoryItem(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
