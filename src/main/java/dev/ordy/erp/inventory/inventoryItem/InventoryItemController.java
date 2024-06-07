package dev.ordy.erp.inventory.inventoryItem;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory-items")
public class InventoryItemController {

    private final InventoryItemService inventoryItemService;

    public InventoryItemController(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }

    @GetMapping
    public List<InventoryItem> all() {
        return inventoryItemService.getAllInventoryItems();
    }

    @PostMapping
    public InventoryItem newInventoryItem(@RequestBody InventoryItem newInventoryItem) {
        return inventoryItemService.createInventoryItem(newInventoryItem);
    }

    @GetMapping("/{id}")
    public InventoryItem one(@PathVariable Long id) {
        return inventoryItemService.getInventoryItemById(id)
                .orElseThrow(() -> new InventoryItemNotFoundException(id));
    }

    @PutMapping("/{id}")
    public InventoryItem replaceInventoryItem(@RequestBody InventoryItem newInventoryItem, @PathVariable Long id) {
        return inventoryItemService.updateInventoryItem(id, newInventoryItem);
    }

    @DeleteMapping("/{id}")
    public void deleteInventoryItem(@PathVariable Long id) {
        inventoryItemService.deleteInventoryItem(id);
    }
}
