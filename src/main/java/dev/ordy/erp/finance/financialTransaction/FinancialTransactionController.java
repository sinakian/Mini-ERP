package dev.ordy.erp.finance.financialTransaction;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/financial-transactions")
public class FinancialTransactionController {

    private final FinancialTransactionService transactionService;

    public FinancialTransactionController(FinancialTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<FinancialTransaction> all() {
        return transactionService.getAllTransactions();
    }

    @PostMapping
    public FinancialTransaction newFinancialTransaction(@RequestBody FinancialTransaction newFinancialTransaction) {
        return transactionService.createTransaction(
                newFinancialTransaction.getFinancialTransactionType(),
                newFinancialTransaction.getTransactionReferenceType(),
                newFinancialTransaction.getCurrency(),
                newFinancialTransaction.getAccount(),
                newFinancialTransaction.getAmount(),
                newFinancialTransaction.getReferenceId()
        );
    }

    @GetMapping("/{id}")
    public FinancialTransaction one(@PathVariable Long id) {
        return transactionService.getTransactionById(id)
                .orElseThrow(() -> new FinancialTransactionNotFoundException(id));
    }

    @PutMapping("/{id}")
    public FinancialTransaction replaceFinancialTransaction(@RequestBody FinancialTransaction newFinancialTransaction, @PathVariable Long id) {
        return transactionService.getTransactionById(id)
                .map(financialTransaction -> {
                    financialTransaction.setFinancialTransactionType(newFinancialTransaction.getFinancialTransactionType());
                    financialTransaction.setTransactionReferenceType(newFinancialTransaction.getTransactionReferenceType());
                    financialTransaction.setCurrency(newFinancialTransaction.getCurrency());
                    financialTransaction.setAccount(newFinancialTransaction.getAccount());
                    financialTransaction.setAmount(newFinancialTransaction.getAmount());
                    financialTransaction.setReferenceId(newFinancialTransaction.getReferenceId());
                    return transactionService.updateTransaction(financialTransaction);
                })
                .orElseGet(() -> {
                    newFinancialTransaction.setId(id);
                    return transactionService.updateTransaction(newFinancialTransaction);
                });
    }

    @DeleteMapping("/{id}")
    public void deleteFinancialTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}
