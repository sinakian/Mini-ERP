package dev.ordy.erp.finance.financialTransaction;

import org.springframework.data.jpa.repository.JpaRepository;

interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {

}
