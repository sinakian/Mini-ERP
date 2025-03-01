package dev.ordy.erp.business.item_category;

import dev.ordy.erp.business.business.Business;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ItemCategoryService(ItemCategoryRepository itemCategoryRepository, ApplicationEventPublisher eventPublisher) {
        this.itemCategoryRepository = itemCategoryRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public ItemCategory createItemCategory(String name, String description, Business business, Long parentCategoryId) {
        ItemCategory parentCategory = null;
        if (parentCategoryId != null) {
            parentCategory = itemCategoryRepository.findById(parentCategoryId)
                    .orElseThrow(() -> new ItemCategoryNotFoundException(parentCategoryId));
        }

        ItemCategory itemCategory = new ItemCategory(name, description, business, parentCategory);
        itemCategoryRepository.save(itemCategory);
        eventPublisher.publishEvent(new ItemCategoryCreateEvent(this, itemCategory));
        return itemCategory;
    }

    @Transactional
    public void deleteItemCategory(Long itemCategoryId) {
        itemCategoryRepository.deleteById(itemCategoryId);
    }

    @Transactional
    public ItemCategory updateItemCategory(Long itemCategoryId, String name, String description, Long parentCategoryId) {
        Optional<ItemCategory> optionalItemCategory = itemCategoryRepository.findById(itemCategoryId);
        if (optionalItemCategory.isPresent()) {
            ItemCategory itemCategory = optionalItemCategory.get();
            itemCategory.setName(name);
            itemCategory.setDescription(description);

            if (parentCategoryId != null) {
                ItemCategory parentCategory = itemCategoryRepository.findById(parentCategoryId)
                        .orElseThrow(() -> new ItemCategoryNotFoundException(parentCategoryId));
                itemCategory.setParentCategory(parentCategory);
            } else {
                itemCategory.setParentCategory(null);
            }

            return itemCategoryRepository.save(itemCategory);
        } else {
            throw new ItemCategoryNotFoundException(itemCategoryId);
        }
    }

    @Transactional(readOnly = true)
    public ItemCategory getItemCategoryById(Long itemCategoryId) {
        return itemCategoryRepository.findById(itemCategoryId)
                .orElseThrow(() -> new ItemCategoryNotFoundException(itemCategoryId));
    }

    @Transactional(readOnly = true)
    public List<ItemCategory> getItemCategoriesByBusiness(Long businessId) {
        return itemCategoryRepository.findByBusinessId(businessId);
    }

    @Transactional(readOnly = true)
    public List<ItemCategory> getRootCategoriesByBusiness(Long businessId) {
        return itemCategoryRepository.findByBusinessIdAndParentCategoryIsNull(businessId);
    }

    @Transactional(readOnly = true)
    public List<ItemCategory> getSubcategories(Long parentCategoryId) {
        return itemCategoryRepository.findByParentCategoryId(parentCategoryId);
    }

    @Transactional(readOnly = true)
    public List<ItemCategory> getAllItemCategories() {
        return itemCategoryRepository.findAll();
    }
}