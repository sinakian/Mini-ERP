package dev.ordy.erp.finance.itemPrice;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item-prices")
public class ItemPriceController {

    private final ItemPriceService itemPriceService;

    public ItemPriceController(ItemPriceService itemPriceService) {
        this.itemPriceService = itemPriceService;
    }

    @GetMapping
    public List<ItemPrice> all() {
        return itemPriceService.getAllItemPrices();
    }

    @PostMapping
    public ItemPrice newItemPrice(@RequestBody ItemPrice newItemPrice) {
        return itemPriceService.createItemPrice(
                newItemPrice.getInventoryItem(),
                newItemPrice.getPrice(),
                newItemPrice.getUnit(),
                newItemPrice.getCurrency()
        );
    }

    @GetMapping("/{id}")
    public ItemPrice one(@PathVariable Long id) {
        return itemPriceService.getItemPriceById(id)
                .orElseThrow(() -> new ItemPriceNotFoundException(id));
    }

    @PutMapping("/{id}")
    public ItemPrice replaceItemPrice(@RequestBody ItemPrice newItemPrice, @PathVariable Long id) {
        return itemPriceService.updateItemPrice(id, newItemPrice);
    }

    @DeleteMapping("/{id}")
    public void deleteItemPrice(@PathVariable Long id) {
        itemPriceService.deleteItemPrice(id);
    }
}