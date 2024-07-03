package dev.ordy.erp.sales.invoice;

import dev.ordy.erp.business.account.Account;
import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.common.Currency;
import dev.ordy.erp.sales.order.Order;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "INVOICE")
@EntityListeners(AuditingEntityListener.class)
public class Invoice {


    public enum InvoiceStatus {
        DRAFT, PENDING, CANCELED, INVOICE, SALES_RETURN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Account customer;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @CreatedDate
    private LocalDateTime createdAt;

    private double totalItemPrice;
    private double totalLogisticPrice;
    private double discountInPercent;
    private double discountInCurrency;
    private double total;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private String paymentType;

    @CreatedBy
    private String createdBy;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @LastModifiedBy
    private String lastModifiedBy;

    public Invoice() {}

    public Invoice(Business business, Account customer,Order order, double totalItemPrice, double totalLogisticPrice, double discountInPercent, double discountInCurrency,double total, Currency currency, String paymentType, String createdBy, InvoiceStatus invoiceStatus) {
        this.business = business;
        this.customer = customer;
        this.order = order;
        this.totalItemPrice = totalItemPrice;
        this.totalLogisticPrice = totalLogisticPrice;
        this.discountInPercent = discountInPercent;
        this.discountInCurrency = discountInCurrency;
        this.total=total;
        this.currency = currency;
        this.paymentType = paymentType;
        this.createdBy = createdBy;
        this.invoiceStatus = invoiceStatus;
    }

    public Long getId() {
        return id;
    }

    public Business getBusiness() {
        return business;
    }

    public Account getCustomer() {
        return customer;
    }

    public Order getOrder() {
        return order;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public double getTotalItemPrice() {
        return totalItemPrice;
    }

    public double getTotalLogisticPrice() {
        return totalLogisticPrice;
    }

    public double getDiscountInPercent() {
        return discountInPercent;
    }

    public double getDiscountInCurrency() {
        return discountInCurrency;
    }

    public double getTotal() {
        return total;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public void setCustomer(Account customer) {
        this.customer = customer;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setTotalItemPrice(double totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public void setTotalLogisticPrice(double totalLogisticPrice) {
        this.totalLogisticPrice = totalLogisticPrice;
    }

    public void setDiscountInPercent(double discountInPercent) {
        this.discountInPercent = discountInPercent;
    }

    public void setDiscountInCurrency(double discountInCurrency) {
        this.discountInCurrency = discountInCurrency;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice)) return false;
        Invoice invoice = (Invoice) o;
        return Double.compare(invoice.totalItemPrice, totalItemPrice) == 0 &&
                Double.compare(invoice.totalLogisticPrice, totalLogisticPrice) == 0 &&
                Double.compare(invoice.discountInPercent, discountInPercent) == 0 &&
                Double.compare(invoice.discountInCurrency, discountInCurrency) == 0 &&
                Objects.equals(id, invoice.id) &&
                Objects.equals(business, invoice.business) &&
                Objects.equals(customer, invoice.customer) &&
                Objects.equals(order, invoice.order) &&
                Objects.equals(createdAt, invoice.createdAt) &&
                currency == invoice.currency &&
                Objects.equals(paymentType, invoice.paymentType) &&
                Objects.equals(createdBy, invoice.createdBy) &&
                invoiceStatus == invoice.invoiceStatus &&
                Objects.equals(lastModifiedDate, invoice.lastModifiedDate) &&
                Objects.equals(lastModifiedBy, invoice.lastModifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, business, customer,order, createdAt, totalItemPrice, totalLogisticPrice, discountInPercent, discountInCurrency, currency, paymentType, createdBy, invoiceStatus, lastModifiedDate, lastModifiedBy);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", business=" + (business != null ? business.getId() : null) +
                ", customer=" + (customer != null ? customer.getId() : null) +
                ", order=" + (order != null ? order.getId() : null) +
                ", createdAt=" + createdAt +
                ", totalItemPrice=" + totalItemPrice +
                ", totalLogisticPrice=" + totalLogisticPrice +
                ", discountInPercent=" + discountInPercent +
                ", discountInCurrency=" + discountInCurrency +
                ", currency=" + currency +
                ", paymentType='" + paymentType + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", invoiceStatus=" + invoiceStatus +
                ", lastModifiedDate=" + lastModifiedDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                '}';
    }
}
