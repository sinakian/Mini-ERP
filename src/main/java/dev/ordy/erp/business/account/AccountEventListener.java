package dev.ordy.erp.business.account;

import dev.ordy.erp.common.Currency;
import dev.ordy.erp.common.FinancialStatus;
import dev.ordy.erp.finance.accountBalance.AccountBalanceService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccountEventListener implements ApplicationListener<AccountCreateEvent> {

    private final AccountBalanceService accountBalanceService;

    public AccountEventListener(AccountBalanceService accountBalanceService) {
        this.accountBalanceService = accountBalanceService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(AccountCreateEvent event) {
        Account account = event.getAccount();

        // Save the AccountBalance object
        accountBalanceService.createAccountBalance(account, 0.0, FinancialStatus.NEUTRAL, Currency.TOMAN);

        // Log a message
        System.out.println("Account Creation Event is completely done");
    }
}

