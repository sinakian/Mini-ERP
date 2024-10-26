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
    public FinancialTransaction newFinancialTransaction(@RequestBody long financialReceipt_id) {
        return transactionService.createTransaction(financialReceipt_id);
    }

    @GetMapping("/{id}")
    public FinancialTransaction one(@PathVariable Long id) {
        return transactionService.getTransactionById(id)
                .orElseThrow(() -> new FinancialTransactionNotFoundException(id));
    }


}
