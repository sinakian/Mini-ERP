package dev.ordy.erp.business.item;

class ItemNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    ItemNotFoundException(Long id) {
        super("Could not find  item " + id);
    }
}
