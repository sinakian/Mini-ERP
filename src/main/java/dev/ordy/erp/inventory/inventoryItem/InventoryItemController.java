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
    public List<InventoryItem> getInventoryItemsByBusiness(@RequestParam Long businessId) {
        return inventoryItemService.getItemsByBusiness(businessId);
    }

    @PostMapping
    public InventoryItem newInventoryItem(@RequestBody InventoryItem newInventoryItem) {
        return inventoryItemService.createInventoryItem(newInventoryItem);
    }

    @GetMapping("/{id}")
    public InventoryItem one(@PathVariable Long id) {
        return inventoryItemService.getInventoryItemById(id);
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
