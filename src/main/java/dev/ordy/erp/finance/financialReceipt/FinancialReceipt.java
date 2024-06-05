package dev.ordy.erp.finance.financialReceipt;


import dev.ordy.erp.business.account.Account;
import dev.ordy.erp.common.BalanceStatus;
import dev.ordy.erp.common.Currency;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "FINANCIAL_RECEIPT")
@EntityListeners(AuditingEntityListener.class)
public class FinancialReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private Double balance;

    @Enumerated(EnumType.STRING)
    private BalanceStatus balanceStatus;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    FinancialReceipt() {}

    public FinancialReceipt(Account account, Double balance, BalanceStatus balanceStatus, Currency currency) {
        this.account = account;
        this.balance = balance;
        this.balanceStatus = balanceStatus;
        this.currency = currency;
    }

    public Long getId() {
        return this.id;
    }

    public Account getAccount() {
        return this.account;
    }

    public Double getBalance() {
        return this.balance;
    }

    public BalanceStatus getBalanceStatus() {
        return this.balanceStatus;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public String getRole() {
        return this.role;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setBalanceStatus(BalanceStatus balanceStatus) {
        this.balanceStatus = balanceStatus;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FinancialReceipt)) return false;
        FinancialReceipt that = (FinancialReceipt) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(account, that.account) &&
                Objects.equals(balance, that.balance) &&
                balanceStatus == that.balanceStatus &&
                currency == that.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, balance, balanceStatus, currency);
    }

    @Override
    public String toString() {
        return "Financial Receipt{" +
                "id=" + id +
                ", account=" + (account != null ? account.getId() : null) +
                ", balance=" + balance +
                ", balanceStatus=" + balanceStatus +
                ", currency=" + currency +
                '}';
    }
}
