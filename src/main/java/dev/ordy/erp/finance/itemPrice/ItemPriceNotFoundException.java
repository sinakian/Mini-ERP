package dev.ordy.erp.finance.itemPrice;

class ItemPriceNotFoundException extends RuntimeException {

    ItemPriceNotFoundException(Long id) {
        super("Could not find item price" + id);
    }
}
