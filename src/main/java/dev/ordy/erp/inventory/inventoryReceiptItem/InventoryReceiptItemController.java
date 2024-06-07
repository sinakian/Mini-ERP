package dev.ordy.erp.inventory.inventoryReceiptItem;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory-receipt-items")
public class InventoryReceiptItemController {

    private final InventoryReceiptItemService inventoryReceiptItemService;

    public InventoryReceiptItemController(InventoryReceiptItemService inventoryReceiptItemService) {
        this.inventoryReceiptItemService = inventoryReceiptItemService;
    }

    @GetMapping
    public List<InventoryReceiptItem> all() {
        return inventoryReceiptItemService.getAllInventoryReceiptItems();
    }

    @PostMapping
    public InventoryReceiptItem newInventoryReceiptItem(@RequestBody InventoryReceiptItem newInventoryReceiptItem) {
        return inventoryReceiptItemService.createInventoryReceiptItem(newInventoryReceiptItem);
    }

    @GetMapping("/{id}")
    public InventoryReceiptItem one(@PathVariable Long id) {
        return inventoryReceiptItemService.getInventoryReceiptItemById(id)
                .orElseThrow(() -> new InventoryReceiptItemNotFoundException(id));
    }

    @PutMapping("/{id}")
    public InventoryReceiptItem replaceInventoryReceiptItem(@RequestBody InventoryReceiptItem newInventoryReceiptItem, @PathVariable Long id) {
        return inventoryReceiptItemService.updateInventoryReceiptItem(id, newInventoryReceiptItem);
    }

    @DeleteMapping("/{id}")
    public void deleteInventoryReceiptItem(@PathVariable Long id) {
        inventoryReceiptItemService.deleteInventoryReceiptItem(id);
    }
}
