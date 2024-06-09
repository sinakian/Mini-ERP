package dev.ordy.erp.finance.accountBalance;

import dev.ordy.erp.common.Currency;
import dev.ordy.erp.business.account.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountBalanceService {

    private final AccountBalanceRepository accountBalanceRepository;

    public AccountBalanceService(AccountBalanceRepository accountBalanceRepository) {
        this.accountBalanceRepository = accountBalanceRepository;
    }

    public AccountBalance createAccountBalance(Account account, Double balance, BalanceStatus balanceStatus, Currency currency) {
        AccountBalance accountBalance = new AccountBalance(account, balance, balanceStatus, currency);
        return accountBalanceRepository.save(accountBalance);
    }

    public List<AccountBalance> getAllAccountBalances() {
        return accountBalanceRepository.findAll();
    }

    public Optional<AccountBalance> getAccountBalanceByAccount(Optional<Account> account) {
        return accountBalanceRepository.findByAccount(account);
    }

    public void updateAccountBalance(AccountBalance accountBalance) {
        accountBalanceRepository.save(accountBalance);
    }

    public void deleteAccountBalance(Long id) {
        accountBalanceRepository.deleteById(id);
    }
}
