package dev.ordy.erp.finance.itemPrice;

class ItemPriceNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    ItemPriceNotFoundException(Long id) {
        super("Could not find item price " + id);
    }
}