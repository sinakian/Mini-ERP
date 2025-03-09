package dev.ordy.erp.finance.itemPrice;

import dev.ordy.erp.inventory.inventoryItem.InventoryItem;
import dev.ordy.erp.common.Unit;
import dev.ordy.erp.common.Currency;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemPriceService {

    private final ItemPriceRepository itemPriceRepository;

    public ItemPriceService(ItemPriceRepository itemPriceRepository) {
        this.itemPriceRepository = itemPriceRepository;
    }

    @Transactional(readOnly = true)
    public List<ItemPrice> getAllItemPrices() {
        return itemPriceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ItemPrice> getItemPriceById(Long id) {
        return itemPriceRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public ItemPrice getItemPriceByInventoryItem(InventoryItem inventoryItem) {
        return itemPriceRepository.findByInventoryItem(inventoryItem)
                .orElseThrow(() -> new ItemPriceNotFoundException(inventoryItem.getId()));
    }

    @Transactional
    public ItemPrice createItemPrice(InventoryItem inventoryItem, Double price, Unit unit, Currency currency) {
        ItemPrice itemPrice = new ItemPrice(inventoryItem, price, unit, currency);
        return itemPriceRepository.save(itemPrice);
    }

    @Transactional
    public ItemPrice updateItemPrice(Long id, ItemPrice newItemPrice) {
        return itemPriceRepository.findById(id)
                .map(itemPrice -> {
                    itemPrice.setInventoryItem(newItemPrice.getInventoryItem());
                    itemPrice.setPrice(newItemPrice.getPrice());
                    itemPrice.setUnit(newItemPrice.getUnit());
                    itemPrice.setCurrency(newItemPrice.getCurrency());
                    return itemPriceRepository.save(itemPrice);
                })
                .orElseThrow(() -> new ItemPriceNotFoundException(id));
    }

    @Transactional
    public void deleteItemPrice(Long id) {
        itemPriceRepository.deleteById(id);
    }
}