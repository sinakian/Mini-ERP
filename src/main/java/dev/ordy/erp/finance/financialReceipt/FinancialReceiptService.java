package dev.ordy.erp.finance.financialReceipt;

import dev.ordy.erp.business.account.Account;
import dev.ordy.erp.common.Currency;
import dev.ordy.erp.common.FinancialStatus;
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

    public FinancialReceipt createFinancialReceipt(Account account,
                                                   Double amount,
                                                   FinancialReceiptReferenceType referenceType,
                                                   FinancialStatus receiptType,
                                                   long referenceId,
                                                   Currency currency,
                                                   TransactionStatus transactionStatus
    ) {
        FinancialReceipt financialReceipt = new FinancialReceipt(account,
                amount,
                referenceType,
                receiptType,
                referenceId,
                currency,
                transactionStatus
        );
        return financialReceiptRepository.save(financialReceipt);
    }

    public FinancialReceipt getFinancialReceiptById(Long id) {
        return financialReceiptRepository.findById(id)
                .orElseThrow(() -> new FinancialReceiptNotFoundException(id));
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

    @Transactional
    public FinancialReceipt setTransactionStatusToCompleted(Long receiptId) {
        Optional<FinancialReceipt> receiptOptional = financialReceiptRepository.findById(receiptId);

        if (receiptOptional.isEmpty()) {
            throw new IllegalArgumentException("FinancialReceipt with ID " + receiptId + " not found.");
        }

        FinancialReceipt financialReceipt = receiptOptional.get();
        financialReceipt.setTransactionStatus(TransactionStatus.COMPLETED);

        return financialReceiptRepository.save(financialReceipt);
    }
}
