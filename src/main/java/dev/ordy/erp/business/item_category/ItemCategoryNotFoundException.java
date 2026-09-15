package dev.ordy.erp.business.item_category;

class ItemCategoryNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    ItemCategoryNotFoundException(Long id) {
        super("Could not find item category " + id);
    }
}