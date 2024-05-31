package dev.ordy.erp.inventory.inventoryReceiptItem;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory-receipt-items")
class InventoryReceiptItemController {

    private final InventoryReceiptItemRepository repository;

    InventoryReceiptItemController(InventoryReceiptItemRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    List<InventoryReceiptItem> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping
    InventoryReceiptItem newInventoryReceiptItem(@RequestBody InventoryReceiptItem newInventoryReceiptItem) {
        return repository.save(newInventoryReceiptItem);
    }

    // Single item

    @GetMapping("/{id}")
    InventoryReceiptItem one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new InventoryReceiptItemNotFoundException(id));
    }

//    @PutMapping("/{id}")
//    InventoryReceiptItem replaceInventoryReceiptItem(@RequestBody InventoryReceiptItem newInventoryReceiptItem, @PathVariable Long id) {
//
//        return repository.findById(id)
//                .map(inventoryReceiptItem -> {
//                    inventoryReceiptItem.setName(newInventoryReceiptItem.getName());
//                    inventoryReceiptItem.setRole(newInventoryReceiptItem.getRole());
//                    return repository.save(inventoryReceiptItem);
//                })
//                .orElseGet(() -> {
//                    newInventoryReceiptItem.setId(id);
//                    return repository.save(newInventoryReceiptItem);
//                });
//    }

    @DeleteMapping("/{id}")
    void deleteInventoryReceiptItem(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
