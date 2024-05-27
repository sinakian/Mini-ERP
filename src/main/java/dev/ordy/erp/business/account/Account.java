package dev.ordy.erp.business.account;

import java.util.Objects;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import dev.ordy.erp.business.business.Business;


@Entity
@Table(name = "BUSINESS_ACCOUNT")
@EntityListeners(AuditingEntityListener.class)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private AccountCategory accountCategory;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String firstName;
    private String lastName;
    private String fullName;
    private String role;


    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    Account() {}

    Account(String firstName, String lastName, String fullName, String role, Business business, AccountType accountType, AccountCategory accountCategory, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.role = role;
        this.business = business;
        this.accountType = accountType;
        this.accountCategory = accountCategory;
        this.gender = gender;
    }

    public Long getId() {
        return this.id;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public AccountCategory getAccountCategory() {
        return this.accountCategory;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Gender getGender() {
        return this.gender;
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

    public Business getBusiness() {
        return business;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setAccountCategory(AccountCategory accountCategory) {
        this.accountCategory = accountCategory;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Account))
            return false;
        Account account = (Account) o;
        return Objects.equals(this.id, account.id)
                && Objects.equals(this.fullName, account.fullName)
                && Objects.equals(this.role, account.role)
                && Objects.equals(business, account.business);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.fullName, this.role,this.business);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountType=" + accountType +
                ", accountCategory=" + accountCategory +
                ", gender='" + gender +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role='" + role + '\'' +
                ", businessId=" + (business != null ? business.getId() : null) +
                ", businessName=" + (business != null ? business.getName() : null) +
                '}';
    }
}
