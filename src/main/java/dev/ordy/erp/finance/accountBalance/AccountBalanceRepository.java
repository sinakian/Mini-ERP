package dev.ordy.erp.finance.accountBalance;

import dev.ordy.erp.business.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Long> {
    Optional<AccountBalance> findByAccount(Account account);
}
