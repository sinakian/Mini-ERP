package dev.ordy.erp.inventory.inventoryReceipt;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory-receipts")
class InventoryReceiptController {

    private final InventoryReceiptRepository repository;

    InventoryReceiptController(InventoryReceiptRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    List<InventoryReceipt> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping
    InventoryReceipt newInventoryReceipt(@RequestBody InventoryReceipt newInventoryReceipt) {
        return repository.save(newInventoryReceipt);
    }

    // Single item

    @GetMapping("/{id}")
    InventoryReceipt one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new InventoryReceiptNotFoundException(id));
    }

//    @PutMapping("/{id}")
//    InventoryReceipt replaceInventoryReceipt(@RequestBody InventoryReceipt newInventoryReceipt, @PathVariable Long id) {
//
//        return repository.findById(id)
//                .map(inventoryReceipt -> {
//                    return repository.save(inventoryReceipt);
//                })
//                .orElseGet(() -> {
//                    newInventoryReceipt.setId(id);
//                    return repository.save(newInventoryReceipt);
//                });
//    }

    @DeleteMapping("/{id}")
    void deleteInventoryReceipt(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
