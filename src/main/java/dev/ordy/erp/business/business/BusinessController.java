package dev.ordy.erp.business.business;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class BusinessController {

    private final BusinessRepository repository;

    BusinessController(BusinessRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/businesses")
    List<Business> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/businesses")
    Business newBusiness(@RequestBody Business newBusiness) {
        return repository.save(newBusiness);
    }

    // Single item

    @GetMapping("/businesses/{id}")
    Business one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new BusinessNotFoundException(id));
    }

    @PutMapping("/businesses/{id}")
    Business replaceBusiness(@RequestBody Business newBusiness, @PathVariable Long id) {

        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newBusiness.getName());
                    employee.setRole(newBusiness.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newBusiness.setId(id);
                    return repository.save(newBusiness);
                });
    }

    @DeleteMapping("/businesses/{id}")
    void deleteBusiness(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
