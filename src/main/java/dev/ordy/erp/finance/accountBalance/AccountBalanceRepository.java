package dev.ordy.erp.finance.accountBalance;

import dev.ordy.erp.business.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Long> {
    AccountBalance findByAccount(Account account);
}
