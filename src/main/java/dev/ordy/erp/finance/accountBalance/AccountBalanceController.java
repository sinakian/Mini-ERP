package dev.ordy.erp.finance.accountBalance;

import dev.ordy.erp.business.account.Account;
import dev.ordy.erp.business.account.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account-balances")
class AccountBalanceController {

    private final AccountBalanceService accountBalanceService;
    private final AccountService accountService;

    AccountBalanceController(AccountBalanceService accountBalanceService, AccountService accountService) {
        this.accountBalanceService = accountBalanceService;
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountBalance> getAllAccountBalances() {
        return accountBalanceService.getAllAccountBalances();
    }

    @GetMapping("/{accountId}")
    AccountBalance getAccountBalance(@PathVariable Long accountId) {
        // Assume you have a method to get an Account object by its ID
        Optional<Account> account = accountService.getAccountById(accountId);
        return accountBalanceService.getAccountBalanceByAccount(account)
                .orElseThrow(() -> new RuntimeException("Account balance not found for account id: " + accountId));
    }

    @PostMapping
    AccountBalance newAccountBalance(@RequestBody AccountBalance newAccountBalance) {
        return accountBalanceService.createAccountBalance(
                newAccountBalance.getAccount(),
                newAccountBalance.getBalance(),
                newAccountBalance.getBalanceStatus(),
                newAccountBalance.getCurrency()
        );
    }

    @PutMapping("/{id}")
    AccountBalance replaceAccountBalance(@RequestBody AccountBalance newAccountBalance, @PathVariable Long id) {
        newAccountBalance.setId(id);
        accountBalanceService.updateAccountBalance(newAccountBalance);
        return newAccountBalance;
    }

    @DeleteMapping("/{id}")
    void deleteAccountBalance(@PathVariable Long id) {
        accountBalanceService.deleteAccountBalance(id);
    }
}
