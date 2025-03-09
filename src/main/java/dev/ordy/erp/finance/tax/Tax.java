package dev.ordy.erp.finance.tax;

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
@Table(name = "TAX")
@EntityListeners(AuditingEntityListener.class)
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private Double rate;

    @Column
    private String description;

    @Column(nullable = false)
    private Boolean active;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false)
    private String businessId;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    Tax() {}

    public Tax(String name, String code, Double rate, String description, Boolean active, Currency currency, String businessId) {
        this.name = name;
        this.code = code;
        this.rate = rate;
        this.description = description;
        this.active = active;
        this.currency = currency;
        this.businessId = businessId;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public Double getRate() {
        return this.rate;
    }

    public String getDescription() {
        return this.description;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public String getBusinessId() {
        return this.businessId;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tax)) return false;
        Tax tax = (Tax) o;
        return Objects.equals(id, tax.id) &&
                Objects.equals(name, tax.name) &&
                Objects.equals(code, tax.code) &&
                Objects.equals(rate, tax.rate) &&
                Objects.equals(description, tax.description) &&
                Objects.equals(active, tax.active) &&
                currency == tax.currency &&
                Objects.equals(businessId, tax.businessId) &&
                Objects.equals(createdDate, tax.createdDate) &&
                Objects.equals(lastModifiedDate, tax.lastModifiedDate) &&
                Objects.equals(createdBy, tax.createdBy) &&
                Objects.equals(lastModifiedBy, tax.lastModifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, rate, description, active, currency, businessId, createdDate, lastModifiedDate, createdBy, lastModifiedBy);
    }

    @Override
    public String toString() {
        return "Tax{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", rate=" + rate +
                ", description='" + description + '\'' +
                ", active=" + active +
                ", currency=" + currency +
                ", businessId='" + businessId + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                '}';
    }
}