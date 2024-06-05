package dev.ordy.erp.finance.financialReceipt;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/financial-receipts")
class FinancialReceiptController {

    private final FinancialReceiptRepository repository;

    FinancialReceiptController(FinancialReceiptRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    List<FinancialReceipt> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping
    FinancialReceipt newFinancialReceipt(@RequestBody FinancialReceipt newFinancialReceipt) {
        return repository.save(newFinancialReceipt);
    }

    // Single item

    @GetMapping("/{id}")
    FinancialReceipt one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new FinancialReceiptNotFoundException(id));
    }

    @PutMapping("/{id}")
    FinancialReceipt replaceFinancialReceipt(@RequestBody FinancialReceipt newFinancialReceipt, @PathVariable Long id) {

        return repository.findById(id)
                .map(financialReceipt -> {
                    financialReceipt.setRole(newFinancialReceipt.getRole());
                    return repository.save(financialReceipt);
                })
                .orElseGet(() -> {
                    newFinancialReceipt.setId(id);
                    return repository.save(newFinancialReceipt);
                });
    }

    @DeleteMapping("/{id}")
    void deleteFinancialReceipt(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
