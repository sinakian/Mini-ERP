package dev.ordy.erp.sales.order;

import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.business.account.Account;
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
@Table(name = "BUSINESS_ORDER")
@EntityListeners(AuditingEntityListener.class)
public class Order {


    public enum OrderStatus {
        DRAFT,
        PENDING,
        CANCELED,
        INVOICE,
        SALES_RETURN
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


    @CreatedDate
    private LocalDateTime createdAt;

    private double totalItemPrice;
    private double totalLogisticPrice;
    private double discountInPercent;
    private double discountInCurrency;
    private double total;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private ConfirmationState confirmationState;

    private String paymentType;



    @CreatedBy
    private String createdBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus = OrderStatus.PENDING;


    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @LastModifiedBy
    private String lastModifiedBy;

    public Order() {}

    public Order(Business business,
                 Account customer,
                 double totalItemPrice,
                 double totalLogisticPrice,
                 double discountInPercent,
                 double discountInCurrency,
                 double total,
                 Currency currency,
                 ConfirmationState confirmationState,
                 String paymentType,
                 String createdBy,
                 OrderStatus orderStatus
    ) {
        this.business = business;
        this.customer = customer;
        this.totalItemPrice = totalItemPrice;
        this.totalLogisticPrice = totalLogisticPrice;
        this.discountInPercent = discountInPercent;
        this.discountInCurrency = discountInCurrency;
        this.total=total;
        this.currency = currency;
        this.confirmationState=confirmationState;
        this.paymentType = paymentType;
        this.createdBy = createdBy;
        this.orderStatus = orderStatus;
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

    public  ConfirmationState getConfirmationState() {return  confirmationState;}

    public String getPaymentType() {
        return paymentType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
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

    public void setTotal(double total){this.total = total;}

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setConfirmationState(ConfirmationState confirmationState){this.confirmationState = confirmationState;}

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
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
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Double.compare(order.totalItemPrice, totalItemPrice) == 0 &&
                Double.compare(order.totalLogisticPrice, totalLogisticPrice) == 0 &&
                Double.compare(order.discountInPercent, discountInPercent) == 0 &&
                Double.compare(order.discountInCurrency, discountInCurrency) == 0 &&
                Objects.equals(id, order.id) &&
                Objects.equals(business, order.business) &&
                Objects.equals(customer, order.customer) &&
                Objects.equals(createdAt, order.createdAt) &&
                currency == order.currency &&
                Objects.equals(paymentType, order.paymentType) &&
                Objects.equals(createdBy, order.createdBy) &&
                orderStatus == order.orderStatus &&
                Objects.equals(lastModifiedDate, order.lastModifiedDate) &&
                Objects.equals(lastModifiedBy, order.lastModifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, business, customer, createdAt,
                totalItemPrice, totalLogisticPrice, discountInPercent,
                discountInCurrency, currency, paymentType, createdBy,
                orderStatus, lastModifiedDate, lastModifiedBy);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", business=" + (business != null ? business.getId() : null) +
                ", customer=" + (customer != null ? customer.getId() : null) +
                ", createdAt=" + createdAt +
                ", totalItemPrice=" + totalItemPrice +
                ", totalLogisticPrice=" + totalLogisticPrice +
                ", discountInPercent=" + discountInPercent +
                ", discountInCurrency=" + discountInCurrency +
                ", discountInCurrency=" + total +
                ", currency=" + currency +
                ", paymentType='" + paymentType + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", orderStatus=" + orderStatus +
                ", lastModifiedDate=" + lastModifiedDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                '}';
    }
}
