package dev.ordy.erp.inventory.inventory;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.ordy.erp.business.business.Business;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "INVENTORY")
@EntityListeners(AuditingEntityListener.class)
public class Inventory {

    public enum InventoryType {
        MATERIAL, PRODUCT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    @JsonBackReference
    private Business business;

    @Enumerated(EnumType.STRING)
    private InventoryType inventoryType;

    private String title;

    private String role;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    Inventory() {}

    public Inventory(Business business, InventoryType inventoryType, String title, String role) {
        this.business = business;
        this.inventoryType = inventoryType;
        this.title = title;
        this.role = role;
    }

    public Long getId() {
        return this.id;
    }

    public Business getBusiness() {
        return this.business;
    }

    public InventoryType getInventoryType() {
        return this.inventoryType;
    }

    public String getTitle() {
        return this.title;
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

    public void setBusiness(Business business) {
        this.business = business;
    }

    public void setInventoryType(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inventory)) return false;
        Inventory inventory = (Inventory) o;
        return Objects.equals(id, inventory.id) &&
                Objects.equals(business, inventory.business) &&
                inventoryType == inventory.inventoryType &&
                Objects.equals(title, inventory.title) &&
                Objects.equals(role, inventory.role) &&
                Objects.equals(createdDate, inventory.createdDate) &&
                Objects.equals(lastModifiedDate, inventory.lastModifiedDate) &&
                Objects.equals(createdBy, inventory.createdBy) &&
                Objects.equals(lastModifiedBy, inventory.lastModifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, business, inventoryType, title, role, createdDate, lastModifiedDate, createdBy, lastModifiedBy);
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", business=" + (business != null ? business.getId() : null) +
                ", inventoryType=" + inventoryType +
                ", title='" + title + '\'' +
                ", role='" + role + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                '}';
    }
}
