package dev.ordy.erp.finance.financialTransaction;

import dev.ordy.erp.business.account.Account;
import dev.ordy.erp.common.Currency;
import dev.ordy.erp.common.FinancialStatus;

import dev.ordy.erp.finance.financialReceipt.FinancialReceipt;
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
    private FinancialStatus financialTransactionType;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @OneToOne
    @JoinColumn(name = "financialReceipt_id",nullable = false)
    private FinancialReceipt financialReceipt;

    private Double amount;

    private Double lastBalance;

    @Enumerated(EnumType.STRING)
    private FinancialStatus lastBalanceStatus;

    private Double newBalance;

    @Enumerated(EnumType.STRING)
    private FinancialStatus newBalanceStatus;


    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    FinancialTransaction() {}

    public FinancialTransaction(FinancialStatus financialTransactionType,
                                Currency currency, Account account,FinancialReceipt financialReceipt,Double amount, Double lastBalance,
                                FinancialStatus lastBalanceStatus,
                                Double newBalance, FinancialStatus newBalanceStatus) {
        this.financialTransactionType = financialTransactionType;
        this.currency = currency;
        this.account = account;
        this.amount = amount;
        this.financialReceipt=financialReceipt;
        this.lastBalance = lastBalance;
        this.lastBalanceStatus = lastBalanceStatus;
        this.newBalance = newBalance;
        this.newBalanceStatus = newBalanceStatus;
    }

    public Long getId() {
        return this.id;
    }

    public FinancialStatus getFinancialTransactionType() {
        return this.financialTransactionType;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public Account getAccount() {
        return this.account;
    }

    public FinancialReceipt getFinancialReceipt() {
        return this.financialReceipt;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Double getLastBalance() {
        return this.lastBalance;
    }

    public FinancialStatus getLastBalanceStatus() {
        return this.lastBalanceStatus;
    }

    public Double getNewBalance() {
        return this.newBalance;
    }

    public FinancialStatus getNewBalanceStatus() {
        return this.newBalanceStatus;
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

    public void setFinancialTransactionType(FinancialStatus financialTransactionType) {
        this.financialTransactionType = financialTransactionType;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setFinancialReceipt(FinancialReceipt financialReceipt) {
        this.financialReceipt = financialReceipt;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setLastBalance(Double lastBalance) {
        this.lastBalance = lastBalance;
    }

    public void setLastBalanceStatus(FinancialStatus lastBalanceStatus) {
        this.lastBalanceStatus = lastBalanceStatus;
    }

    public void setNewBalance(Double newBalance) {
        this.newBalance = newBalance;
    }

    public void setNewBalanceStatus(FinancialStatus newBalanceStatus) {
        this.newBalanceStatus = newBalanceStatus;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FinancialTransaction)) return false;
        FinancialTransaction that = (FinancialTransaction) o;
        return Objects.equals(id, that.id) &&
                financialTransactionType == that.financialTransactionType &&
                currency == that.currency &&
                Objects.equals(account, that.account) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(lastBalance, that.lastBalance) &&
                lastBalanceStatus == that.lastBalanceStatus &&
                Objects.equals(newBalance, that.newBalance) &&
                newBalanceStatus == that.newBalanceStatus &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(lastModifiedBy, that.lastModifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, financialTransactionType,  currency, account, amount, lastBalance, lastBalanceStatus, newBalance, newBalanceStatus,  createdDate, lastModifiedDate, createdBy, lastModifiedBy);
    }

    @Override
    public String toString() {
        return "FinancialTransaction{" +
                "id=" + id +
                ", financialTransactionType=" + financialTransactionType +
                ", currency=" + currency +
                ", account=" + (account != null ? account.getId() : null) +
                ", account=" + (financialReceipt != null ? financialReceipt.getId() : null) +
                ", amount=" + amount +
                ", lastBalance=" + lastBalance +
                ", lastBalanceStatus=" + lastBalanceStatus +
                ", newBalance=" + newBalance +
                ", newBalanceStatus=" + newBalanceStatus +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                '}';
    }
}
