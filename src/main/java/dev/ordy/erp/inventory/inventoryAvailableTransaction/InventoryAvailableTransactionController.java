package dev.ordy.erp.inventory.inventoryAvailableTransaction;

import dev.ordy.erp.inventory.inventoryAvailableTransaction.InventoryAvailableTransaction;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory-available-transactions")
public class InventoryAvailableTransactionController {

    private final InventoryAvailableTransactionService inventoryAvailableTransactionService;

    public InventoryAvailableTransactionController(InventoryAvailableTransactionService inventoryAvailableTransactionService) {
        this.inventoryAvailableTransactionService = inventoryAvailableTransactionService;
    }

    @GetMapping
    public List<InventoryAvailableTransaction> all() {
        return inventoryAvailableTransactionService.getAllInventoryAvailableTransactions();
    }



    @GetMapping("/{id}")
    public InventoryAvailableTransaction one(@PathVariable Long id) {
        return inventoryAvailableTransactionService.getInventoryAvailableTransactionById(id)
                .orElseThrow(() -> new InventoryAvailableTransactionNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public void deleteInventoryTransaction(@PathVariable Long id) {
        inventoryAvailableTransactionService.deleteInventoryAvailableTransaction(id);
    }
}
