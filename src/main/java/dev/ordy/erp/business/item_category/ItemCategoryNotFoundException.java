package dev.ordy.erp.business.item_category;

class ItemCategoryNotFoundException extends RuntimeException {

    ItemCategoryNotFoundException(Long id) {
        super("Could not find item category " + id);
    }
}