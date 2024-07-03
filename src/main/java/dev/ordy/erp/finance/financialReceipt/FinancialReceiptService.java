package dev.ordy.erp.finance.financialReceipt;

import dev.ordy.erp.business.account.Account;
import dev.ordy.erp.common.Currency;
import dev.ordy.erp.finance.financialReceipt.enums.FinancialReceiptReferenceType;
import dev.ordy.erp.finance.financialReceipt.enums.FinancialReceiptType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FinancialReceiptService {

    private final FinancialReceiptRepository financialReceiptRepository;

    public FinancialReceiptService(FinancialReceiptRepository financialReceiptRepository) {
        this.financialReceiptRepository = financialReceiptRepository;
    }

    public FinancialReceipt createFinancialReceipt(Account account, Double amount, FinancialReceiptReferenceType referenceType, FinancialReceiptType receiptType, long referenceId, Currency currency) {
        FinancialReceipt financialReceipt = new FinancialReceipt(account, amount, referenceType, receiptType, referenceId, currency);
        return financialReceiptRepository.save(financialReceipt);
    }

    public Optional<FinancialReceipt> getFinancialReceiptById(Long id) {
        return financialReceiptRepository.findById(id);
    }

    public List<FinancialReceipt> getAllFinancialReceipts() {
        return financialReceiptRepository.findAll();
    }

    public FinancialReceipt updateFinancialReceipt(FinancialReceipt financialReceipt) {
        return financialReceiptRepository.save(financialReceipt);
    }

    public void deleteFinancialReceipt(Long id) {
        financialReceiptRepository.deleteById(id);
    }
}
