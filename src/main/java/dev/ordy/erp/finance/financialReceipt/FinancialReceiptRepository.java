package dev.ordy.erp.finance.financialReceipt;

import dev.ordy.erp.business.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FinancialReceiptRepository extends JpaRepository<FinancialReceipt, Long> {
    Optional<FinancialReceipt> findByAccount(Account account);
}
