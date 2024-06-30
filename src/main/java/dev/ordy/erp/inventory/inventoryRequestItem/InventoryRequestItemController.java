package dev.ordy.erp.inventory.inventoryRequestItem;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory-receipt-items")
public class InventoryRequestItemController {

    private final InventoryRequestItemService inventoryRequestItemService;

    public InventoryRequestItemController(InventoryRequestItemService inventoryRequestItemService) {
        this.inventoryRequestItemService = inventoryRequestItemService;
    }

    @GetMapping
    public List<InventoryRequestItem> all() {
        return inventoryRequestItemService.getAllInventoryRequestItems();
    }

    @PostMapping
    public InventoryRequestItem newInventoryRequestItem(@RequestBody InventoryRequestItem newInventoryRequestItem) {
        return inventoryRequestItemService.createInventoryRequestItem(newInventoryRequestItem);
    }

    @GetMapping("/{id}")
    public InventoryRequestItem one(@PathVariable Long id) {
        return inventoryRequestItemService.getInventoryRequestItemById(id)
                .orElseThrow(() -> new InventoryRequestItemNotFoundException(id));
    }

    @PutMapping("/{id}")
    public InventoryRequestItem replaceInventoryRequestItem(@RequestBody InventoryRequestItem newInventoryRequestItem, @PathVariable Long id) {
        return inventoryRequestItemService.updateInventoryRequestItem(id, newInventoryRequestItem);
    }

    @DeleteMapping("/{id}")
    public void deleteInventoryRequestItem(@PathVariable Long id) {
        inventoryRequestItemService.deleteInventoryRequestItem(id);
    }
}
