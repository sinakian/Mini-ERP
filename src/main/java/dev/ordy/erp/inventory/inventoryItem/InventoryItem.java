package dev.ordy.erp.inventory.inventoryItem;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.business.item.Item;
import dev.ordy.erp.inventory.inventory.Inventory;
import dev.ordy.erp.common.Unit;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "INVENTORY_ITEM")
@EntityListeners(AuditingEntityListener.class)
public class InventoryItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    @JsonBackReference
    private Business business;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    private double realBalance;
    private double availableBalance;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    private String name;
    private String role;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    public InventoryItem() {}

    public InventoryItem(Inventory inventory,Business business, Item item, double realBalance, double availableBalance, Unit unit, String name, String role) {
        this.inventory = inventory;
        this.business = business;
        this.item = item;
        this.realBalance = realBalance;
        this.availableBalance = availableBalance;
        this.unit = unit;
        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return this.id;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public Business getBusiness() {
        return this.business;
    }

    public Item getItem() {
        return this.item;
    }

    public double getQuantity() {
        return this.realBalance;
    }

    public double getAvailableQuantity() {
        return this.availableBalance;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public String getName() {
        return this.name;
    }

    public String getRole() {
        return this.role;
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

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setQuantity(double realBalance) {
        this.realBalance = realBalance;
    }

    public void setAvailableQuantity(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventoryItem)) return false;
        InventoryItem item = (InventoryItem) o;
        return Double.compare(item.realBalance, realBalance) == 0 &&
                Double.compare(item.availableBalance, availableBalance) == 0 &&
                Objects.equals(id, item.id) &&
                Objects.equals(inventory, item.inventory) &&
                Objects.equals(this.item, item.item) &&
                unit == item.unit &&
                Objects.equals(name, item.name) &&
                Objects.equals(role, item.role) &&
                Objects.equals(createdDate, item.createdDate) &&
                Objects.equals(lastModifiedDate, item.lastModifiedDate) &&
                Objects.equals(createdBy, item.createdBy) &&
                Objects.equals(lastModifiedBy, item.lastModifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, inventory, item, realBalance, availableBalance, unit, name, role, createdDate, lastModifiedDate, createdBy, lastModifiedBy);
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "id=" + id +
                ", inventory=" + (inventory != null ? inventory.getId() : null) +
                ", item=" + (item != null ? item.getId() : null) +
                ", realBalance=" + realBalance +
                ", availableBalance=" + availableBalance +
                ", unit=" + unit +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                '}';
    }
}
