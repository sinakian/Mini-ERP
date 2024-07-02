package dev.ordy.erp.inventory.inventoryTransaction;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory-transactions")
public class InventoryTransactionController {

    private final InventoryTransactionService inventoryTransactionService;

    public InventoryTransactionController(InventoryTransactionService inventoryTransactionService) {
        this.inventoryTransactionService = inventoryTransactionService;
    }

    @GetMapping
    public List<InventoryTransaction> all() {
        return inventoryTransactionService.getAllInventoryTransactions();
    }

    @PostMapping
    public InventoryTransaction newInventoryTransaction(@RequestBody InventoryTransaction newInventoryTransaction) {
        return inventoryTransactionService.createInventoryTransaction(newInventoryTransaction);
    }

    @GetMapping("/{id}")
    public InventoryTransaction one(@PathVariable Long id) {
        return inventoryTransactionService.getInventoryTransactionById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteInventoryTransaction(@PathVariable Long id) {
        inventoryTransactionService.deleteInventoryTransaction(id);
    }
}
