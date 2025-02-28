package dev.ordy.erp.finance.financialReceipt;

import dev.ordy.erp.business.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FinancialReceiptRepository extends JpaRepository<FinancialReceipt, Long> {
    Optional<FinancialReceipt> findByAccount(Account account);
    List<FinancialReceipt> findByAccountId(Long accountId);

    // Custom query to find receipts by referenceId and type
    @Query("SELECT r FROM FinancialReceipt r WHERE r.referenceType = :type AND r.referenceId = :refId")
    List<FinancialReceipt> findByReferenceTypeAndReferenceId(@Param("type") FinancialReceiptReferenceType type, @Param("refId") Long refId);
}
