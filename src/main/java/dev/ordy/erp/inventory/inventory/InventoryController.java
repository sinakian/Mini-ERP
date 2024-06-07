package dev.ordy.erp.inventory.inventory;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<Inventory> all() {
        return inventoryService.getAllInventories();
    }

    @PostMapping
    public Inventory newInventory(@RequestBody Inventory newInventory) {
        return inventoryService.createInventory(
                newInventory.getBusiness(),
                newInventory.getInventoryType(),
                newInventory.getTitle(),
                newInventory.getRole()
        );
    }

    @GetMapping("/{id}")
    public Inventory one(@PathVariable Long id) {
        return inventoryService.getInventoryById(id)
                .orElseThrow(() -> new InventoryNotFoundException(id));
    }

    @PutMapping("/{id}")
    public Inventory replaceInventory(@RequestBody Inventory newInventory, @PathVariable Long id) {
        return inventoryService.updateInventory(id, newInventory);
    }

    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
    }
}
