package dev.ordy.erp.business.item;

class ItemNotFoundException extends RuntimeException {

    ItemNotFoundException(Long id) {
        super("Could not find  item " + id);
    }
}
