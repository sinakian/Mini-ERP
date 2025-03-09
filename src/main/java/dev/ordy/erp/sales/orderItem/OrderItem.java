package dev.ordy.erp.sales.orderItem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.ordy.erp.sales.order.Order;
import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.common.Unit;
import dev.ordy.erp.common.Currency;
import dev.ordy.erp.finance.itemPrice.ItemPrice;
import dev.ordy.erp.inventory.inventoryItem.InventoryItem;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "ORDER_ITEM")
@EntityListeners(AuditingEntityListener.class)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private InventoryItem item;

    private double quantity;

    @ManyToOne
    @JoinColumn(name = "item_price_id") //Change in Production to nullable = false
    private ItemPrice itemPrice;

    private double pricePerUnit;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private double totalGrossPrice;
    private double discountCurrency;
    private double discountPercent;
    private double tax;
    private double totalNetPrice;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    public OrderItem() {}

    public OrderItem(Business business,
                     Order order,
                     InventoryItem item,
                     double quantity,
                     ItemPrice itemPrice,
                     double pricePerUnit,
                     Unit unit, Currency currency,
                     double totalGrossPrice,
                     double discountCurrency,
                     double discountPercent,
                     double tax,
                     double totalNetPrice,
                     String createdBy
    ) {
        this.business = business;
        this.order = order;
        this.item = item;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.pricePerUnit = pricePerUnit;
        this.unit = unit;
        this.currency = currency;
        this.totalGrossPrice = totalGrossPrice;
        this.discountCurrency = discountCurrency;
        this.discountPercent = discountPercent;
        this.tax = tax;
        this.totalNetPrice = totalNetPrice;
        this.createdBy = createdBy;
    }

    // Add setter methods to make the entity more update-friendly
    public void setId(Long id) {
        this.id = id;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setItem(InventoryItem item) {
        this.item = item;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setItemPrice(ItemPrice itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setTotalGrossPrice(double totalGrossPrice) {
        this.totalGrossPrice = totalGrossPrice;
    }

    public void setDiscountCurrency(double discountCurrency) {
        this.discountCurrency = discountCurrency;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setTotalNetPrice(double totalNetPrice) {
        this.totalNetPrice = totalNetPrice;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    // Existing getter methods
    public Long getId() {
        return id;
    }

    public Business getBusiness() {
        return business;
    }

    public Order getOrder() {
        return order;
    }

    public InventoryItem getItem() {
        return item;
    }

    public double getQuantity() {
        return quantity;
    }

    public ItemPrice getItemPrice() {
        return itemPrice;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public Unit getUnit() {
        return unit;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getTotalGrossPrice() {
        return totalGrossPrice;
    }

    public double getDiscountCurrency() {
        return discountCurrency;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public double getTax() {
        return tax;
    }

    public double getTotalNetPrice() {
        return totalNetPrice;
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

    // Update totals based on the current quantity and price per unit
    public void recalculateTotals() {
        // Calculate gross price
        this.totalGrossPrice = this.quantity * this.pricePerUnit;

        // Calculate tax amount
        double taxRate = this.tax / this.totalGrossPrice; // Store the tax rate
        this.tax = this.totalGrossPrice * taxRate;

        // Calculate net price after discounts and tax
        double afterDiscount = this.totalGrossPrice - this.discountCurrency -
                (this.totalGrossPrice * (this.discountPercent / 100));
        this.totalNetPrice = afterDiscount + this.tax;
    }

    // Builder pattern for easier updates
    public static class Builder {
        private OrderItem orderItem;

        public Builder() {
            this.orderItem = new OrderItem();
        }

        public Builder(OrderItem existing) {
            this.orderItem = new OrderItem();
            this.orderItem.id = existing.id;
            this.orderItem.business = existing.business;
            this.orderItem.order = existing.order;
            this.orderItem.item = existing.item;
            this.orderItem.quantity = existing.quantity;
            this.orderItem.itemPrice = existing.itemPrice;
            this.orderItem.pricePerUnit = existing.pricePerUnit;
            this.orderItem.unit = existing.unit;
            this.orderItem.currency = existing.currency;
            this.orderItem.totalGrossPrice = existing.totalGrossPrice;
            this.orderItem.discountCurrency = existing.discountCurrency;
            this.orderItem.discountPercent = existing.discountPercent;
            this.orderItem.tax = existing.tax;
            this.orderItem.totalNetPrice = existing.totalNetPrice;
            this.orderItem.createdBy = existing.createdBy;
            this.orderItem.createdDate = existing.createdDate;
            this.orderItem.lastModifiedBy = existing.lastModifiedBy;
            this.orderItem.lastModifiedDate = existing.lastModifiedDate;
        }

        public Builder withId(Long id) {
            this.orderItem.id = id;
            return this;
        }

        public Builder withBusiness(Business business) {
            this.orderItem.business = business;
            return this;
        }

        public Builder withOrder(Order order) {
            this.orderItem.order = order;
            return this;
        }

        public Builder withItem(InventoryItem item) {
            this.orderItem.item = item;
            return this;
        }

        public Builder withQuantity(double quantity) {
            this.orderItem.quantity = quantity;
            return this;
        }

        public Builder withItemPrice(ItemPrice itemPrice) {
            this.orderItem.itemPrice = itemPrice;
            return this;
        }

        public Builder withPricePerUnit(double pricePerUnit) {
            this.orderItem.pricePerUnit = pricePerUnit;
            return this;
        }

        public Builder withUnit(Unit unit) {
            this.orderItem.unit = unit;
            return this;
        }

        public Builder withCurrency(Currency currency) {
            this.orderItem.currency = currency;
            return this;
        }

        public Builder withTotalGrossPrice(double totalGrossPrice) {
            this.orderItem.totalGrossPrice = totalGrossPrice;
            return this;
        }

        public Builder withDiscountCurrency(double discountCurrency) {
            this.orderItem.discountCurrency = discountCurrency;
            return this;
        }

        public Builder withDiscountPercent(double discountPercent) {
            this.orderItem.discountPercent = discountPercent;
            return this;
        }

        public Builder withTax(double tax) {
            this.orderItem.tax = tax;
            return this;
        }

        public Builder withTotalNetPrice(double totalNetPrice) {
            this.orderItem.totalNetPrice = totalNetPrice;
            return this;
        }

        public Builder withCreatedBy(String createdBy) {
            this.orderItem.createdBy = createdBy;
            return this;
        }

        public OrderItem build() {
            return this.orderItem;
        }
    }
}