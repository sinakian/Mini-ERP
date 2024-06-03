package dev.ordy.erp.inventory.inventoryItem;

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
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    private double quantity;
    private double availableQuantity;

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

    InventoryItem() {}

    public InventoryItem(Inventory inventory, Item item, double quantity, double availableQuantity, Unit unit, String name, String role) {
        this.inventory = inventory;
        this.item = item;
        this.quantity = quantity;
        this.availableQuantity = availableQuantity;
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

    public Item getItem() {
        return this.item;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public double getAvailableQuantity() {
        return this.availableQuantity;
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

    public void setItem(Item item) {
        this.item = item;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setAvailableQuantity(double availableQuantity) {
        this.availableQuantity = availableQuantity;
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
        return Double.compare(item.quantity, quantity) == 0 &&
                Double.compare(item.availableQuantity, availableQuantity) == 0 &&
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
        return Objects.hash(id, inventory, item, quantity, availableQuantity, unit, name, role, createdDate, lastModifiedDate, createdBy, lastModifiedBy);
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "id=" + id +
                ", inventory=" + (inventory != null ? inventory.getId() : null) +
                ", item=" + (item != null ? item.getId() : null) +
                ", quantity=" + quantity +
                ", availableQuantity=" + availableQuantity +
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
