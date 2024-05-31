package dev.ordy.erp.finance.itemPrice;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item-prices")
class ItemPriceController {

    private final ItemPriceRepository repository;

    ItemPriceController(ItemPriceRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    List<ItemPrice> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping
    ItemPrice newItemPrice(@RequestBody ItemPrice newItemPrice) {
        return repository.save(newItemPrice);
    }

    // Single item

    @GetMapping("/{id}")
    ItemPrice one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ItemPriceNotFoundException(id));
    }

//    @PutMapping("/{id}")
//    ItemPrice replaceItemPrice(@RequestBody ItemPrice newItemPrice, @PathVariable Long id) {
//
//        return repository.findById(id)
//                .map(itemPrice -> {
//                    itemPrice.setName(newItemPrice.getName());
//                    itemPrice.setRole(newItemPrice.getRole());
//                    return repository.save(itemPrice);
//                })
//                .orElseGet(() -> {
//                    newItemPrice.setId(id);
//                    return repository.save(newItemPrice);
//                });
//    }

    @DeleteMapping("/{id}")
    void deleteItemPrice(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
