package dev.ordy.erp.finance.financialTransaction;

import dev.ordy.erp.finance.accountBalance.AccountBalance;
import dev.ordy.erp.finance.accountBalance.AccountBalanceRepository;
import dev.ordy.erp.business.account.Account;
import dev.ordy.erp.finance.accountBalance.BalanceStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FinancialTransactionEventListener {
    private static final Logger logger = LoggerFactory.getLogger(FinancialTransactionEventListener.class);

    private final AccountBalanceRepository accountBalanceRepository;


    public FinancialTransactionEventListener(AccountBalanceRepository accountBalanceRepository) {
        this.accountBalanceRepository = accountBalanceRepository;
    }

    @EventListener
    @Transactional
    public void handleFinancialTransactionEvent(FinancialTransactionCreateEvent event) {
        FinancialTransaction transaction = event.getTransaction();
        Account account = transaction.getAccount();

        AccountBalance accountBalance = accountBalanceRepository.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("Account balance not found for account id: " + account.getId()));

        Double newBalance = transaction.getNewBalance();
        BalanceStatus newBalanceStatus = transaction.getNewBalanceStatus();

        // Update the account balance
        accountBalance.setBalance(newBalance);
        accountBalance.setBalanceStatus(newBalanceStatus);
        accountBalanceRepository.save(accountBalance);

        // Log balance update
        logger.info("Updated account balance: {}", accountBalance);
    }
}
