package dev.ordy.erp.finance.financialReceipt;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/financial-receipts")
class FinancialReceiptController {

    private final FinancialReceiptService service;

    FinancialReceiptController(FinancialReceiptService service) {
        this.service = service;
    }

    @GetMapping
    List<FinancialReceipt> all() {
        return service.getAllFinancialReceipts();
    }

    @PostMapping
    FinancialReceipt newFinancialReceipt(@RequestBody FinancialReceipt newFinancialReceipt) {
        return service.createFinancialReceipt(
                newFinancialReceipt.getAccount(),
                newFinancialReceipt.getAmount(),
                newFinancialReceipt.getReferenceType(),
                newFinancialReceipt.getReceiptType(),
                newFinancialReceipt.getReferenceId(),
                newFinancialReceipt.getCurrency(),
                newFinancialReceipt.getTransactionStatus()
        );
    }

    @GetMapping("/{id}")
    FinancialReceipt one(@PathVariable Long id) {
        return service.getFinancialReceiptById(id);
    }

    @GetMapping("/by-order/{orderId}")
    List<FinancialReceipt> getByOrderId(@PathVariable Long orderId) {
        return service.getReceiptsByOrderId(orderId);
    }


    @DeleteMapping("/{id}")
    void deleteFinancialReceipt(@PathVariable Long id) {
        service.deleteFinancialReceipt(id);
    }
}
