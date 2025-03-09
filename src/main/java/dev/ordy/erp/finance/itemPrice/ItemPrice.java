package dev.ordy.erp.finance.itemPrice;

import dev.ordy.erp.inventory.inventoryItem.InventoryItem;
import dev.ordy.erp.common.Unit;
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
@Table(name = "ITEM_PRICE")
@EntityListeners(AuditingEntityListener.class)
public class ItemPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventory_item_id", nullable = false)
    private InventoryItem inventoryItem;

    private Double price;

    @Enumerated(EnumType.STRING)
    private Unit unit;

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

    ItemPrice() {}

    public ItemPrice(InventoryItem inventoryItem, Double price, Unit unit, Currency currency) {
        this.inventoryItem = inventoryItem;
        this.price = price;
        this.unit = unit;
        this.currency = currency;
    }

    public Long getId() {
        return this.id;
    }

    public InventoryItem getInventoryItem() {
        return this.inventoryItem;
    }

    public Double getPrice() {
        return this.price;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public Currency getCurrency() {
        return this.currency;
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

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemPrice)) return false;
        ItemPrice itemPrice = (ItemPrice) o;
        return Objects.equals(id, itemPrice.id) &&
                Objects.equals(inventoryItem, itemPrice.inventoryItem) &&
                Objects.equals(price, itemPrice.price) &&
                unit == itemPrice.unit &&
                currency == itemPrice.currency &&
                Objects.equals(createdDate, itemPrice.createdDate) &&
                Objects.equals(lastModifiedDate, itemPrice.lastModifiedDate) &&
                Objects.equals(createdBy, itemPrice.createdBy) &&
                Objects.equals(lastModifiedBy, itemPrice.lastModifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, inventoryItem, price, unit, currency, createdDate, lastModifiedDate, createdBy, lastModifiedBy);
    }

    @Override
    public String toString() {
        return "ItemPrice{" +
                "id=" + id +
                ", inventoryItem=" + (inventoryItem != null ? inventoryItem.getId() : null) +
                ", price=" + price +
                ", unit=" + unit +
                ", currency=" + currency +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                '}';
    }
}