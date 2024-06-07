package dev.ordy.erp.business.item;

import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.business.item.enums.InventoryPolicy;
import dev.ordy.erp.business.item.enums.ItemType;
import dev.ordy.erp.common.Unit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public Item createItem(String name, String role, Business business, InventoryPolicy inventoryPolicy, ItemType itemType, Unit unit) {
        Item item = new Item(name, role, business, inventoryPolicy, itemType, unit);
        return itemRepository.save(item);
    }

    @Transactional
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    @Transactional
    public Item updateItem(Long itemId, String name, String role, InventoryPolicy inventoryPolicy, ItemType itemType, Unit unit) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setName(name);
            item.setRole(role);
            item.setInventoryPolicy(inventoryPolicy);
            item.setItemType(itemType);
            item.setUnit(unit);
            return itemRepository.save(item);
        } else {
            // Handle item not found error
            throw new RuntimeException("Item not found with id: " + itemId);
        }
    }

    @Transactional(readOnly = true)
    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + itemId));
    }

    @Transactional(readOnly = true)
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // Add more methods as needed
}
