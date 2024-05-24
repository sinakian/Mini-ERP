package dev.ordy.erp.supply;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supply-requests")
class SupplyRequestController {

    private final SupplyRequestRepository repository;

    SupplyRequestController(SupplyRequestRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    List<SupplyRequest> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping
    SupplyRequest newSupplyRequest(@RequestBody SupplyRequest newSupplyRequest) {
        return repository.save(newSupplyRequest);
    }

    // Single item

    @GetMapping("/{id}")
    SupplyRequest one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new SupplyRequestNotFoundException(id));
    }

    @PutMapping("/{id}")
    SupplyRequest replaceSupplyRequest(@RequestBody SupplyRequest newSupplyRequest, @PathVariable Long id) {

        return repository.findById(id)
                .map(supplyRequest -> {
                    supplyRequest.setName(newSupplyRequest.getName());
                    supplyRequest.setRole(newSupplyRequest.getRole());
                    return repository.save(supplyRequest);
                })
                .orElseGet(() -> {
                    newSupplyRequest.setId(id);
                    return repository.save(newSupplyRequest);
                });
    }

    @DeleteMapping("/{id}")
    void deleteSupplyRequest(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
