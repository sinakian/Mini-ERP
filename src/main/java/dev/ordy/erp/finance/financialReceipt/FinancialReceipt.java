package dev.ordy.erp.finance.financialReceipt;


import dev.ordy.erp.business.account.Account;
import dev.ordy.erp.common.*;
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

    private Double amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private FinancialReceiptReferenceType referenceType;

    @Enumerated(EnumType.STRING)
    private FinancialStatus receiptType;

    private long referenceId;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus = TransactionStatus.NOT_PROCESSED;

    @Enumerated(EnumType.STRING)
    private ConfirmationState confirmationState = ConfirmationState.PENDING;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    FinancialReceipt() {}

    public FinancialReceipt(Account account,
                            Double amount,
                            FinancialReceiptReferenceType referenceType,
                            FinancialStatus receiptType,
                            long referenceId,
                            Currency currency,
                            TransactionStatus transactionStatus,
                            ConfirmationState confirmationState
    ) {
        this.account=account;
        this.amount=amount;
        this.referenceType=referenceType;
        this.receiptType=receiptType;
        this.referenceId=referenceId;
        this.currency=currency;
        this.transactionStatus=transactionStatus;
        this.confirmationState = confirmationState;
    }


    public Long getId() {
        return this.id;
    }

    public Account getAccount() {
        return this.account;
    }

    public Double getAmount() {
        return this.amount;
    }

    public FinancialReceiptReferenceType getReferenceType() {
        return this.referenceType;
    }

    public FinancialStatus getReceiptType() {
        return this.receiptType;
    }

    public long getReferenceId() {
        return this.referenceId;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public String getRole() {
        return this.role;
    }

    public TransactionStatus getTransactionStatus() {
        return this.transactionStatus;
    }

    public ConfirmationState getConfirmationState() {
        return this.confirmationState;
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

    public void setAmount(Double amount) {this.amount = amount;}

    public void setReferenceType(FinancialReceiptReferenceType referenceType){ this.referenceType=referenceType; }

    public void setReferenceId(long referenceId){this.referenceId=referenceId;}

    public void setReceiptType(FinancialStatus receiptType){this.receiptType=receiptType;}

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus){this.transactionStatus=transactionStatus;}

    public void setConfirmationState(ConfirmationState confirmationState){this.confirmationState=confirmationState;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FinancialReceipt)) return false;
        FinancialReceipt that = (FinancialReceipt) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(account, that.account) &&
                Objects.equals(amount, that.amount) &&
                referenceType == that.referenceType &&
                receiptType == that.receiptType &&
                Objects.equals(referenceId, that.referenceId) &&
                currency == that.currency;

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, amount, referenceType,referenceId,receiptType, currency);
    }

    @Override
    public String toString() {
        return "Financial Receipt{" +
                "id=" + id +
                ", account=" + (account != null ? account.getId() : null) +
                ", amount=" + amount +
                ", referenceType=" + referenceType +
                ", referenceId=" + referenceId +
                ", receiptType=" + receiptType +
                ", currency=" + currency +
                '}';
    }
}
