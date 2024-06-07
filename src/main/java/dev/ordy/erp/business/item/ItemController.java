package dev.ordy.erp.business.item;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
class ItemController {

    private final ItemService itemService;

    ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    List<Item> all() {
        return itemService.getAllItems();
    }
    // end::get-aggregate-root[]

    @PostMapping
    Item newItem(@RequestBody Item newItem) {
        return itemService.createItem(newItem.getName(), newItem.getRole(), newItem.getBusiness(), newItem.getInventoryPolicy(), newItem.getItemType(), newItem.getUnit());
    }


    @GetMapping("/{id}")
    Item one(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @PutMapping("/{id}")
    Item replaceItem(@RequestBody Item newItem, @PathVariable Long id) {
        return itemService.updateItem(id, newItem.getName(), newItem.getRole(), newItem.getInventoryPolicy(), newItem.getItemType(), newItem.getUnit());
    }

    @DeleteMapping("/{id}")
    void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }
}
