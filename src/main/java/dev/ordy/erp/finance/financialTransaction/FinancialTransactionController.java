package dev.ordy.erp.finance.financialTransaction;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/financial-transactions")
class FinancialTransactionController {

    private final FinancialTransactionRepository repository;

    FinancialTransactionController(FinancialTransactionRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    List<FinancialTransaction> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping
    FinancialTransaction newFinancialTransaction(@RequestBody FinancialTransaction newFinancialTransaction) {
        return repository.save(newFinancialTransaction);
    }

    // Single item

    @GetMapping("/{id}")
    FinancialTransaction one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new FinancialTransactionNotFoundException(id));
    }

    @PutMapping("/{id}")
    FinancialTransaction replaceFinancialTransaction(@RequestBody FinancialTransaction newFinancialTransaction, @PathVariable Long id) {

        return repository.findById(id)
                .map(financialTransaction -> {
                    return repository.save(financialTransaction);
                })
                .orElseGet(() -> {
                    newFinancialTransaction.setId(id);
                    return repository.save(newFinancialTransaction);
                });
    }

    @DeleteMapping("/{id}")
    void deleteFinancialTransaction(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
