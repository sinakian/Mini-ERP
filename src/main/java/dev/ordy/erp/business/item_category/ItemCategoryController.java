package dev.ordy.erp.business.item_category;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item-categories")
class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;

    ItemCategoryController(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;
    }

    @GetMapping
    public List<ItemCategory> getItemCategoriesByBusiness(@RequestParam Long businessId) {
        return itemCategoryService.getItemCategoriesByBusiness(businessId);
    }

    @GetMapping("/root")
    public List<ItemCategory> getRootCategoriesByBusiness(@RequestParam Long businessId) {
        return itemCategoryService.getRootCategoriesByBusiness(businessId);
    }

    @GetMapping("/subcategories/{parentId}")
    public List<ItemCategory> getSubcategories(@PathVariable Long parentId) {
        return itemCategoryService.getSubcategories(parentId);
    }

    @PostMapping
    ItemCategory newItemCategory(@RequestBody ItemCategory newItemCategory) {
        return itemCategoryService.createItemCategory(
                newItemCategory.getName(),
                newItemCategory.getDescription(),
                newItemCategory.getBusiness(),
                newItemCategory.getParentCategory() != null ?
                        newItemCategory.getParentCategory().getId() : null
        );
    }

    @GetMapping("/{id}")
    ItemCategory one(@PathVariable Long id) {
        return itemCategoryService.getItemCategoryById(id);
    }

    @PutMapping("/{id}")
    ItemCategory replaceItemCategory(@RequestBody ItemCategory newItemCategory, @PathVariable Long id) {
        return itemCategoryService.updateItemCategory(
                id,
                newItemCategory.getName(),
                newItemCategory.getDescription(),
                newItemCategory.getParentCategory() != null ?
                        newItemCategory.getParentCategory().getId() : null
        );
    }

    @DeleteMapping("/{id}")
    void deleteItemCategory(@PathVariable Long id) {
        itemCategoryService.deleteItemCategory(id);
    }
}