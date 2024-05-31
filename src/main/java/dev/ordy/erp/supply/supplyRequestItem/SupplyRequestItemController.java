package dev.ordy.erp.supply.supplyRequestItem;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supply-request-items")
class SupplyRequestItemController {

    private final SupplyRequestItemRepository repository;

    SupplyRequestItemController(SupplyRequestItemRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    List<SupplyRequestItem> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping
    SupplyRequestItem newSupplyRequestItem(@RequestBody SupplyRequestItem newSupplyRequestItem) {
        return repository.save(newSupplyRequestItem);
    }

    // Single item

    @GetMapping("/{id}")
    SupplyRequestItem one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new SupplyRequestItemNotFoundException(id));
    }

//    @PutMapping("/{id}")
//    SupplyRequestItem replaceSupplyRequestItem(@RequestBody SupplyRequestItem newSupplyRequestItem, @PathVariable Long id) {
//
//        return repository.findById(id)
//                .map(supplyRequestItem -> {
//                    supplyRequestItem.setName(newSupplyRequestItem.getName());
//                    supplyRequestItem.setRole(newSupplyRequestItem.getRole());
//                    return repository.save(supplyRequestItem);
//                })
//                .orElseGet(() -> {
//                    newSupplyRequestItem.setId(id);
//                    return repository.save(newSupplyRequestItem);
//                });
//    }

    @DeleteMapping("/{id}")
    void deleteSupplyRequestItem(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
