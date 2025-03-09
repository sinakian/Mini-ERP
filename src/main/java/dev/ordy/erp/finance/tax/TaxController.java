package dev.ordy.erp.finance.tax;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taxes")
public class TaxController {

    private final TaxService taxService;

    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

    @GetMapping
    public List<Tax> all() {
        return taxService.getAllTaxes();
    }

    @GetMapping("/business/{businessId}")
    public List<Tax> byBusiness(@PathVariable Long businessId) {
        return taxService.getTaxesByBusinessId(businessId);
    }

    @PostMapping
    public Tax newTax(@RequestBody Tax newTax) {
        return taxService.createTax(
                newTax.getName(),
                newTax.getCode(),
                newTax.getRate(),
                newTax.getDescription(),
                newTax.getActive(),
                newTax.getCurrency(),
                newTax.getBusinessId()
        );
    }

    @GetMapping("/{id}")
    public Tax one(@PathVariable Long id) {
        return taxService.getTaxById(id)
                .orElseThrow(() -> new TaxNotFoundException(id));
    }

    @GetMapping("/code/{code}/business/{businessId}")
    public Tax byCode(@PathVariable String code, @PathVariable Long businessId) {
        return taxService.getTaxByCode(code, businessId)
                .orElseThrow(() -> new TaxNotFoundException(code));
    }

    @PutMapping("/{id}")
    public Tax replaceTax(@RequestBody Tax newTax, @PathVariable Long id) {
        return taxService.updateTax(id, newTax);
    }

    @DeleteMapping("/{id}")
    public void deleteTax(@PathVariable Long id) {
        taxService.deleteTax(id);
    }
}
