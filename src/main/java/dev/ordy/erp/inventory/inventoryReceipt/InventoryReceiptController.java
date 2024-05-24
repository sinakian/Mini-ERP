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
    InventoryReceipt newBusiness(@RequestBody InventoryReceipt newInventoryReceipt) {
        return repository.save(newInventoryReceipt);
    }

    // Single item

    @GetMapping("/{id}")
    InventoryReceipt one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new InventoryReceiptNotFoundException(id));
    }

    @PutMapping("/{id}")
    InventoryReceipt replaceBusiness(@RequestBody InventoryReceipt newInventoryReceipt, @PathVariable Long id) {

        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newInventoryReceipt.getName());
                    employee.setRole(newInventoryReceipt.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newInventoryReceipt.setId(id);
                    return repository.save(newInventoryReceipt);
                });
    }

    @DeleteMapping("/{id}")
    void deleteBusiness(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
