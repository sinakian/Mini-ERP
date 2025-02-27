package dev.ordy.erp.finance.financialTransaction;

import dev.ordy.erp.common.FinancialStatus;
import dev.ordy.erp.finance.accountBalance.AccountBalance;
import dev.ordy.erp.finance.accountBalance.AccountBalanceRepository;
import dev.ordy.erp.business.account.Account;
import dev.ordy.erp.finance.accountBalance.AccountBalanceService;
import dev.ordy.erp.finance.accountBalance.BalanceStatus;
import dev.ordy.erp.finance.financialReceipt.FinancialReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class FinancialTransactionEventListener {
    private static final Logger logger = LoggerFactory.getLogger(FinancialTransactionEventListener.class);

    private final AccountBalanceService accountBalanceService;
    private final FinancialReceiptService financialReceiptService;


    public FinancialTransactionEventListener(AccountBalanceService accountBalanceService,
                                             FinancialReceiptService financialReceiptService
    ) {
        this.accountBalanceService = accountBalanceService;
        this.financialReceiptService = financialReceiptService;
    }

    @EventListener
    @Transactional
    public void handleFinancialTransactionEvent(FinancialTransactionCreateEvent event) {
        FinancialTransaction transaction = event.getTransaction();
        Account account = transaction.getAccount();

        AccountBalance accountBalance = accountBalanceService.getAccountBalanceByAccount(account);

        Double newBalance = transaction.getNewBalance();
        FinancialStatus newBalanceStatus = transaction.getNewBalanceStatus();
        Long receiptId = transaction.getFinancialReceipt().getId();

        // Update the account balance
        accountBalance.setBalance(newBalance);
        accountBalance.setBalanceStatus(newBalanceStatus);
        accountBalanceService.updateAccountBalance(accountBalance);

        //Update Transaction Status on Financial Receipt
        financialReceiptService.setTransactionStatusToCompleted(receiptId);


        // Log balance update
        logger.info("Updated account balance: {}", accountBalance);
    }
}
