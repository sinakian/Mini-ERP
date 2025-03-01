package dev.ordy.erp.business.item;

import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.business.item.enums.InventoryPolicy;
import dev.ordy.erp.business.item.enums.ItemType;
import dev.ordy.erp.business.item_category.ItemCategory;
import dev.ordy.erp.business.item_category.ItemCategoryService;
import dev.ordy.erp.common.Unit;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final ItemCategoryService itemCategoryService;

    public ItemService(ItemRepository itemRepository,
                       ApplicationEventPublisher eventPublisher,
                       ItemCategoryService itemCategoryService) {
        this.itemRepository = itemRepository;
        this.eventPublisher = eventPublisher;
        this.itemCategoryService = itemCategoryService;
    }

    @Transactional
    public Item createItem(String name, String role, Business business,
                           InventoryPolicy inventoryPolicy, ItemType itemType,
                           Unit unit, Long categoryId) {

        ItemCategory category = null;
        if (categoryId != null) {
            category = itemCategoryService.getItemCategoryById(categoryId);
        }

        Item item = new Item(name, role, business, inventoryPolicy, itemType, unit, category);
        itemRepository.save(item);
        eventPublisher.publishEvent(new ItemCreateEvent(this, item));
        return item;
    }

    @Transactional
    public Item createItem(String name, String role, Business business,
                           InventoryPolicy inventoryPolicy, ItemType itemType, Unit unit) {
        return createItem(name, role, business, inventoryPolicy, itemType, unit, null);
    }

    @Transactional
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    @Transactional
    public Item updateItem(Long itemId, String name, String role,
                           InventoryPolicy inventoryPolicy, ItemType itemType,
                           Unit unit, Long categoryId) {

        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setName(name);
            item.setRole(role);
            item.setInventoryPolicy(inventoryPolicy);
            item.setItemType(itemType);
            item.setUnit(unit);

            if (categoryId != null) {
                ItemCategory category = itemCategoryService.getItemCategoryById(categoryId);
                item.setCategory(category);
            } else {
                item.setCategory(null);
            }

            return itemRepository.save(item);
        } else {
            throw new ItemNotFoundException(itemId);
        }
    }

    @Transactional
    public Item updateItem(Long itemId, String name, String role,
                           InventoryPolicy inventoryPolicy, ItemType itemType, Unit unit) {
        return updateItem(itemId, name, role, inventoryPolicy, itemType, unit, null);
    }

    @Transactional(readOnly = true)
    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
    }

    public List<Item> getItemsByBusiness(Long businessId) {
        return itemRepository.findByBusinessId(businessId);
    }

    @Transactional(readOnly = true)
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // Add methods to find items by category
    @Transactional(readOnly = true)
    public List<Item> getItemsByCategory(Long categoryId) {
        // You'll need to add this method to the ItemRepository
        // return itemRepository.findByCategoryId(categoryId);

        // For now, we'll filter the results in code
        return itemRepository.findAll().stream()
                .filter(item -> item.getCategory() != null &&
                        item.getCategory().getId().equals(categoryId))
                .toList();
    }
}