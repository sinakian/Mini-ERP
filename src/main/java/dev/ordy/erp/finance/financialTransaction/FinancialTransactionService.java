package dev.ordy.erp.finance.financialTransaction;

import dev.ordy.erp.business.account.Account;
import dev.ordy.erp.common.Currency;
import dev.ordy.erp.finance.accountBalance.BalanceStatus;
import dev.ordy.erp.finance.financialReceipt.FinancialReceipt;
import dev.ordy.erp.finance.financialTransaction.enums.FinancialTransactionType;
import dev.ordy.erp.finance.financialTransaction.enums.TransactionReferenceType;
import dev.ordy.erp.finance.accountBalance.AccountBalance;
import dev.ordy.erp.finance.accountBalance.AccountBalanceRepository;
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
    private final AccountBalanceRepository accountBalanceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public FinancialTransactionService(FinancialTransactionRepository financialTransactionRepository,
                                       AccountBalanceRepository accountBalanceRepository,
                                       ApplicationEventPublisher eventPublisher) {
        this.financialTransactionRepository = financialTransactionRepository;
        this.accountBalanceRepository = accountBalanceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public FinancialTransaction createTransaction(FinancialTransactionType transactionType, TransactionReferenceType referenceType,
                                                  Currency currency, Account account, FinancialReceipt financialReceipt, double amount, long referenceId) {
        AccountBalance accountBalance = accountBalanceRepository.findByAccount(Optional.ofNullable(account))
                .orElseThrow(() -> new RuntimeException("Account balance not found for account id: " + account.getId()));

        double lastBalance = accountBalance.getBalance();
        BalanceStatus lastBalanceStatus = accountBalance.getBalanceStatus();

        double newBalance;
        BalanceStatus newBalanceStatus;

        if (transactionType == FinancialTransactionType.CREDIT
                && (lastBalanceStatus==BalanceStatus.CREDIT || lastBalanceStatus==BalanceStatus.NEUTRAL)) {
            newBalance = lastBalance + amount;
            newBalanceStatus=BalanceStatus.CREDIT;
        } else if (transactionType == FinancialTransactionType.DEBIT
                && (lastBalanceStatus==BalanceStatus.DEBIT || lastBalanceStatus==BalanceStatus.NEUTRAL)) {
            newBalance = lastBalance + amount;
            newBalanceStatus=BalanceStatus.DEBIT;
        } else if ( lastBalance==amount
                && ((transactionType == FinancialTransactionType.DEBIT && lastBalanceStatus==BalanceStatus.CREDIT)
                || (transactionType == FinancialTransactionType.CREDIT && lastBalanceStatus==BalanceStatus.DEBIT))) {
            newBalance = lastBalance - amount;
            newBalanceStatus=BalanceStatus.NEUTRAL;
        }else if ((transactionType == FinancialTransactionType.CREDIT
                && lastBalanceStatus==BalanceStatus.DEBIT
                && amount<lastBalance)
                || (transactionType == FinancialTransactionType.DEBIT
                && lastBalanceStatus==BalanceStatus.CREDIT
                && amount>lastBalance)) {

            newBalance = Math.abs(amount-lastBalance);
            newBalanceStatus=BalanceStatus.DEBIT;

        }else if ((transactionType == FinancialTransactionType.DEBIT
                && lastBalanceStatus==BalanceStatus.CREDIT
                && amount<lastBalance)
                || (transactionType == FinancialTransactionType.CREDIT
                && lastBalanceStatus==BalanceStatus.DEBIT
                && amount>lastBalance)) {
            newBalance = Math.abs(amount-lastBalance);
            newBalanceStatus=BalanceStatus.CREDIT;
        } else {
            throw new IllegalArgumentException("Unsupported transaction type: " + transactionType);
        }




        // Create the financial transaction
        FinancialTransaction transaction = new FinancialTransaction(
                transactionType, referenceType, currency, account, financialReceipt, amount, lastBalance, lastBalanceStatus,
                newBalance, newBalanceStatus, referenceId
        );

        // Save the transaction
        financialTransactionRepository.save(transaction);

        // Update the account balance
        accountBalance.setBalance(newBalance);
        accountBalance.setBalanceStatus(newBalanceStatus);
        accountBalanceRepository.save(accountBalance);

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
