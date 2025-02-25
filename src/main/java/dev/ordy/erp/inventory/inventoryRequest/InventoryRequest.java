package dev.ordy.erp.inventory.inventoryRequest;

import dev.ordy.erp.business.business.Business; // Assuming you have a BusinessSettings class in the business package
import dev.ordy.erp.inventory.inventory.Inventory;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "INVENTORY_REQUEST")
@EntityListeners(AuditingEntityListener.class)
public class InventoryRequest {

    public enum ReferenceType {
        INVOICE, DIRECT, WASTE
    }

    public enum Status {
        PENDING, DELIVERED, CANCELED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    private double quantity;
    private LocalDateTime dueDate;
    private LocalDateTime deliveredDate;

    @Enumerated(EnumType.STRING)
    private ReferenceType referenceType;

    private long referenceId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    InventoryRequest() {}

    public InventoryRequest(Business business, Inventory inventory, double quantity, LocalDateTime dueDate, LocalDateTime deliveredDate, ReferenceType referenceType, long referenceId, Status status) {
        this.business = business;
        this.inventory = inventory;
        this.quantity = quantity;
        this.dueDate = dueDate;
        this.deliveredDate = deliveredDate;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.status = status;
    }

    public Long getId() {
        return this.id;
    }

    public Business getBusiness() {
        return this.business;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public LocalDateTime getDueDate() {
        return this.dueDate;
    }

    public LocalDateTime getDeliveredDate() {
        return this.deliveredDate;
    }

    public ReferenceType getReferenceType() {
        return this.referenceType;
    }

    public long getReferenceId() {
        return this.referenceId;
    }

    public Status getStatus() {
        return this.status;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public void setDeliveredDate(LocalDateTime deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public void setReferenceType(ReferenceType referenceType) {
        this.referenceType = referenceType;
    }

    public void setReferenceId(long referenceId) {
        this.referenceId = referenceId;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventoryRequest)) return false;
        InventoryRequest request = (InventoryRequest) o;
        return Double.compare(request.quantity, quantity) == 0 &&
                Objects.equals(id, request.id) &&
                Objects.equals(business, request.business) &&
                Objects.equals(inventory, request.inventory) &&
                Objects.equals(dueDate, request.dueDate) &&
                Objects.equals(deliveredDate, request.deliveredDate) &&
                referenceType == request.referenceType &&
                Objects.equals(referenceId, request.referenceId) &&
                status == request.status &&
                Objects.equals(createdDate, request.createdDate) &&
                Objects.equals(lastModifiedDate, request.lastModifiedDate) &&
                Objects.equals(createdBy, request.createdBy) &&
                Objects.equals(lastModifiedBy, request.lastModifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, business, inventory, quantity, dueDate, deliveredDate, referenceType, referenceId, status, createdDate, lastModifiedDate, createdBy, lastModifiedBy);
    }

    @Override
    public String toString() {
        return "Inventory Request{" +
                "id=" + id +
                ", business=" + (business != null ? business.getId() : null) +
                ", inventory=" + (inventory != null ? inventory.getId() : null) +
                ", quantity=" + quantity +
                ", dueDate=" + dueDate +
                ", deliveredDate=" + deliveredDate +
                ", referenceType=" + referenceType +
                ", referenceId='" + referenceId + '\'' +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                '}';
    }
}
