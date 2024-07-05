package dev.ordy.erp.sales.orderItem;

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
    @JoinColumn(name = "item_price_id", nullable = false)
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

    public OrderItem(Business business, Order order, InventoryItem item, double quantity, ItemPrice itemPrice, double pricePerUnit, Unit unit, Currency currency, double totalGrossPrice, double discountCurrency, double discountPercent, double tax, double totalNetPrice, String createdBy) {
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

    // Add Getter Methods

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
}
