package dev.ordy.erp.business.item;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
class ItemController {

    private final ItemRepository repository;

    ItemController(ItemRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    List<Item> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping
    Item newItem(@RequestBody Item newItem) {
        return repository.save(newItem);
    }

    // Single item

    @GetMapping("/{id}")
    Item one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }

    @PutMapping("/{id}")
    Item replaceItem(@RequestBody Item newItem, @PathVariable Long id) {

        return repository.findById(id)
                .map(business -> {
                    business.setName(newItem.getName());
                    business.setRole(newItem.getRole());
                    return repository.save(business);
                })
                .orElseGet(() -> {
                    newItem.setId(id);
                    return repository.save(newItem);
                });
    }

    @DeleteMapping("/{id}")
    void deleteItem(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
