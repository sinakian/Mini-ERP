package dev.ordy.erp.finance.financialTransaction;

import dev.ordy.erp.business.account.Account;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "FINANCIAL_TRANSACTION")
@EntityListeners(AuditingEntityListener.class)
public class FinancialTransaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FinancialTransactionType financialTransactionType;

    @Enumerated(EnumType.STRING)
    private TransactionReferenceType transactionReferenceType;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private Double amount;

    private Double lastBalance;

    @Enumerated(EnumType.STRING)
    private BalanceStatus lastBalanceStatus;

    private Double newBalance;

    @Enumerated(EnumType.STRING)
    private BalanceStatus newBalanceStatus;

    private String referenceId;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    FinancialTransaction() {}

    FinancialTransaction(FinancialTransactionType financialTransactionType, TransactionReferenceType transactionReferenceType,
                         Currency currency, Account account, Double amount, Double lastBalance, BalanceStatus lastBalanceStatus,
                         Double newBalance, BalanceStatus newBalanceStatus, String referenceId) {
        this.financialTransactionType = financialTransactionType;
        this.transactionReferenceType = transactionReferenceType;
        this.currency = currency;
        this.account = account;
        this.amount = amount;
        this.lastBalance = lastBalance;
        this.lastBalanceStatus = lastBalanceStatus;
        this.newBalance = newBalance;
        this.newBalanceStatus = newBalanceStatus;
        this.referenceId = referenceId;
    }

    public Long getId() {
        return this.id;
    }

    public FinancialTransactionType getFinancialTransactionType() {
        return this.financialTransactionType;
    }

    public TransactionReferenceType getTransactionReferenceType() {
        return this.transactionReferenceType;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public Account getAccount() {
        return this.account;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Double getLastBalance() {
        return this.lastBalance;
    }

    public BalanceStatus getLastBalanceStatus() {
        return this.lastBalanceStatus;
    }

    public Double getNewBalance() {
        return this.newBalance;
    }

    public BalanceStatus getNewBalanceStatus() {
        return this.newBalanceStatus;
    }

    public String getReferenceId() {
        return this.referenceId;
    }

    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFinancialTransactionType(FinancialTransactionType financialTransactionType) {
        this.financialTransactionType = financialTransactionType;
    }

    public void setTransactionReferenceType(TransactionReferenceType transactionReferenceType) {
        this.transactionReferenceType = transactionReferenceType;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setLastBalance(Double lastBalance) {
        this.lastBalance = lastBalance;
    }

    public void setLastBalanceStatus(BalanceStatus lastBalanceStatus) {
        this.lastBalanceStatus = lastBalanceStatus;
    }

    public void setNewBalance(Double newBalance) {
        this.newBalance = newBalance;
    }

    public void setNewBalanceStatus(BalanceStatus newBalanceStatus) {
        this.newBalanceStatus = newBalanceStatus;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FinancialTransaction)) return false;
        FinancialTransaction that = (FinancialTransaction) o;
        return Objects.equals(id, that.id) &&
                financialTransactionType == that.financialTransactionType &&
                transactionReferenceType == that.transactionReferenceType &&
                currency == that.currency &&
                Objects.equals(account, that.account) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(lastBalance, that.lastBalance) &&
                lastBalanceStatus == that.lastBalanceStatus &&
                Objects.equals(newBalance, that.newBalance) &&
                newBalanceStatus == that.newBalanceStatus &&
                Objects.equals(referenceId, that.referenceId) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(lastModifiedBy, that.lastModifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, financialTransactionType, transactionReferenceType, currency, account, amount, lastBalance, lastBalanceStatus, newBalance, newBalanceStatus, referenceId, createdDate, lastModifiedDate, createdBy, lastModifiedBy);
    }

    @Override
    public String toString() {
        return "FinancialTransaction{" +
                "id=" + id +
                ", financialTransactionType=" + financialTransactionType +
                ", transactionReferenceType=" + transactionReferenceType +
                ", currency=" + currency +
                ", account=" + (account != null ? account.getId() : null) +
                ", amount=" + amount +
                ", lastBalance=" + lastBalance +
                ", lastBalanceStatus=" + lastBalanceStatus +
                ", newBalance=" + newBalance +
                ", newBalanceStatus=" + newBalanceStatus +
                ", referenceId='" + referenceId + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                '}';
    }
}
