package dev.ordy.erp.finance.financialTransaction;

import dev.ordy.erp.business.account.Account;
import dev.ordy.erp.common.Currency;
import dev.ordy.erp.finance.accountBalance.BalanceStatus;
import dev.ordy.erp.finance.financialTransaction.enums.FinancialTransactionType;
import dev.ordy.erp.finance.financialTransaction.enums.TransactionReferenceType;
import dev.ordy.erp.finance.accountBalance.AccountBalance;
import dev.ordy.erp.finance.accountBalance.AccountBalanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                                                  Currency currency, Account account, Double amount, String referenceId) {
        AccountBalance accountBalance = accountBalanceRepository.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("Account balance not found for account id: " + account.getId()));

        Double lastBalance = accountBalance.getBalance();
        BalanceStatus lastBalanceStatus = accountBalance.getBalanceStatus();
        Double newBalance = lastBalance + amount;
        BalanceStatus newBalanceStatus = newBalance > 0 ? BalanceStatus.CREDIT : BalanceStatus.DEBT;

        // Create the financial transaction
        FinancialTransaction transaction = new FinancialTransaction(
                transactionType, referenceType, currency, account, amount, lastBalance, lastBalanceStatus,
                newBalance, newBalanceStatus, referenceId
        );

        // Save the transaction
        financialTransactionRepository.save(transaction);

        // Publish the event
        eventPublisher.publishEvent(new FinancialTransactionEvent(this, transaction));

        // Log transaction creation
        logger.info("Created transaction: {}", transaction);

        return transaction;
    }
}
