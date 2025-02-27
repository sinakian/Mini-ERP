package dev.ordy.erp.finance.financialTransaction;

import dev.ordy.erp.business.account.Account;
import dev.ordy.erp.common.Currency;
import dev.ordy.erp.common.FinancialStatus;
import dev.ordy.erp.finance.accountBalance.AccountBalanceService;
import dev.ordy.erp.finance.financialReceipt.FinancialReceipt;
import dev.ordy.erp.finance.financialReceipt.FinancialReceiptService;
import dev.ordy.erp.finance.accountBalance.AccountBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FinancialTransactionService {
    private static final Logger logger = LoggerFactory.getLogger(FinancialTransactionService.class);

    private final FinancialTransactionRepository financialTransactionRepository;
    private final AccountBalanceService accountBalanceService;
    private final ApplicationEventPublisher eventPublisher;
    private final FinancialReceiptService financialReceiptService;

    public FinancialTransactionService(FinancialTransactionRepository financialTransactionRepository,
                                       AccountBalanceService accountBalanceService,
                                       ApplicationEventPublisher eventPublisher,
                                       FinancialReceiptService financialReceiptService
    ) {
        this.financialTransactionRepository = financialTransactionRepository;
        this.eventPublisher = eventPublisher;
        this.accountBalanceService = accountBalanceService;
        this.financialReceiptService = financialReceiptService;

    }

    @Transactional
    public FinancialTransaction createTransaction(Long financialReceiptId) {
        FinancialReceipt receipt=financialReceiptService.getFinancialReceiptById(financialReceiptId);
        AccountBalance accountBalance = accountBalanceService.getAccountBalanceByAccount(receipt.getAccount());
        FinancialStatus transactionType=receipt.getReceiptType();
        double lastBalance = accountBalance.getBalance();
        double amount = receipt.getAmount();
        FinancialStatus lastBalanceStatus = accountBalance.getBalanceStatus();

        double newBalance;
        FinancialStatus newBalanceStatus;

        if (transactionType == FinancialStatus.CREDIT
                && (lastBalanceStatus==FinancialStatus.CREDIT || lastBalanceStatus==FinancialStatus.NEUTRAL)) {
            newBalance = lastBalance + amount;
            newBalanceStatus=FinancialStatus.CREDIT;
        } else if (transactionType == FinancialStatus.DEBIT
                && (lastBalanceStatus==FinancialStatus.DEBIT || lastBalanceStatus==FinancialStatus.NEUTRAL)) {
            newBalance = lastBalance + amount;
            newBalanceStatus=FinancialStatus.DEBIT;
        } else if ( lastBalance==amount
                && ((transactionType == FinancialStatus.DEBIT && lastBalanceStatus==FinancialStatus.CREDIT)
                || (transactionType == FinancialStatus.CREDIT && lastBalanceStatus==FinancialStatus.DEBIT))) {
            newBalance = lastBalance - amount;
            newBalanceStatus=FinancialStatus.NEUTRAL;
        }else if ((transactionType == FinancialStatus.CREDIT
                && lastBalanceStatus==FinancialStatus.DEBIT
                && amount<lastBalance)
                || (transactionType == FinancialStatus.DEBIT
                && lastBalanceStatus==FinancialStatus.CREDIT
                && amount>lastBalance)) {

            newBalance = Math.abs(amount-lastBalance);
            newBalanceStatus=FinancialStatus.DEBIT;

        }else if ((transactionType == FinancialStatus.DEBIT
                && lastBalanceStatus==FinancialStatus.CREDIT
                && amount<lastBalance)
                || (transactionType == FinancialStatus.CREDIT
                && lastBalanceStatus==FinancialStatus.DEBIT
                && amount>lastBalance)) {
            newBalance = Math.abs(amount-lastBalance);
            newBalanceStatus=FinancialStatus.CREDIT;
        } else {
            throw new IllegalArgumentException("Unsupported transaction type: " + transactionType);
        }



        // Create and Save the financial transaction
        FinancialTransaction transaction = new FinancialTransaction(
                receipt.getReceiptType(),
                receipt.getCurrency(),
                receipt.getAccount(),
                receipt,
                receipt.getAmount(),
                lastBalance,
                lastBalanceStatus,
                newBalance,
                newBalanceStatus
        );
        financialTransactionRepository.save(transaction);


        // Update the account balance
        accountBalance.setBalance(newBalance);
        accountBalance.setBalanceStatus(newBalanceStatus);
        accountBalanceService.updateAccountBalance(accountBalance);

        // Publish the event
        eventPublisher.publishEvent(new FinancialTransactionCreateEvent(this, transaction));

        // Log transaction creation
        logger.info("Created transaction: {}", transaction);

        return transaction;
    }


    @Transactional(readOnly = true)
    public List<FinancialTransaction> getAllTransactions() {
        return financialTransactionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<FinancialTransaction> getTransactionById(Long id) {
        return financialTransactionRepository.findById(id);
    }

    @Transactional
    public FinancialTransaction updateTransaction(FinancialTransaction transaction) {
        return financialTransactionRepository.save(transaction);
    }

    @Transactional
    public void deleteTransaction(Long id) {
        financialTransactionRepository.deleteById(id);
    }
}
