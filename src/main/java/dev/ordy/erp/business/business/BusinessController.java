package dev.ordy.erp.business.business;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/businesses")
class BusinessController {

    private final BusinessRepository repository;
    private final BusinessService businessService;

    BusinessController(BusinessRepository repository, BusinessService businessService) {
        this.repository = repository;
        this.businessService = businessService;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    List<Business> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping
    public Business createBusiness(@RequestBody Business business) {
        return businessService.createBusiness(business);
    }

    // Single item

    @GetMapping("{id}")
    Business one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new BusinessNotFoundException(id));
    }

    @PutMapping("{id}")
    Business replaceBusiness(@RequestBody Business newBusiness, @PathVariable Long id) {

        return repository.findById(id)
                .map(business -> {
                    return repository.save(business);
                })
                .orElseGet(() -> {
                    return repository.save(newBusiness);
                });
    }

    @DeleteMapping("{id}")
    void deleteBusiness(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
