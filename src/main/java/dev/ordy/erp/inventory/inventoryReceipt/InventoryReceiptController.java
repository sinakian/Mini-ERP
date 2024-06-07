package dev.ordy.erp.inventory.inventoryReceipt;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory-receipts")
public class InventoryReceiptController {

    private final InventoryReceiptService inventoryReceiptService;

    public InventoryReceiptController(InventoryReceiptService inventoryReceiptService) {
        this.inventoryReceiptService = inventoryReceiptService;
    }

    @GetMapping
    public List<InventoryReceipt> all() {
        return inventoryReceiptService.getAllInventoryReceipts();
    }

    @PostMapping
    public InventoryReceipt newInventoryReceipt(@RequestBody InventoryReceipt newInventoryReceipt) {
        return inventoryReceiptService.createInventoryReceipt(newInventoryReceipt);
    }

    @GetMapping("/{id}")
    public InventoryReceipt one(@PathVariable Long id) {
        return inventoryReceiptService.getInventoryReceiptById(id)
                .orElseThrow(() -> new InventoryReceiptNotFoundException(id));
    }

    @PutMapping("/{id}")
    public InventoryReceipt replaceInventoryReceipt(@RequestBody InventoryReceipt newInventoryReceipt, @PathVariable Long id) {
        return inventoryReceiptService.updateInventoryReceipt(id, newInventoryReceipt);
    }

    @DeleteMapping("/{id}")
    public void deleteInventoryReceipt(@PathVariable Long id) {
        inventoryReceiptService.deleteInventoryReceipt(id);
    }
}
