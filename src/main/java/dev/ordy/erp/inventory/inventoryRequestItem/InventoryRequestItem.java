package dev.ordy.erp.inventory.inventoryRequestItem;

import dev.ordy.erp.inventory.inventoryRequest.InventoryRequest;
import dev.ordy.erp.inventory.inventoryItem.InventoryItem;
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
@Table(name = "INVENTORY_REQUEST_ITEM")
@EntityListeners(AuditingEntityListener.class)
public class InventoryRequestItem {


    public enum Status {
        DELIVERED, PENDING, CANCELED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventory_request_id", nullable = false)
    private InventoryRequest inventoryRequest;

    @ManyToOne
    @JoinColumn(name = "inventory_item_id", nullable = false)
    private InventoryItem inventoryItem;

    private double requestQuantity;
    private double deliveredQuantity;
    private double pendingQuantity;
    private LocalDateTime dueDate;
    private LocalDateTime deliveredDate;

    @Enumerated(EnumType.STRING)
    private Unit unit;

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

    public InventoryRequestItem() {}

    public InventoryRequestItem(InventoryRequest inventoryRequest, InventoryItem inventoryItem, double requestQuantity, double deliveredQuantity, double pendingQuantity, LocalDateTime dueDate, LocalDateTime deliveredDate, Unit unit, Status status) {
        this.inventoryRequest = inventoryRequest;
        this.inventoryItem = inventoryItem;
        this.requestQuantity = requestQuantity;
        this.deliveredQuantity = deliveredQuantity;
        this.pendingQuantity = pendingQuantity;
        this.dueDate = dueDate;
        this.deliveredDate = deliveredDate;
        this.unit = unit;
        this.status = status;
    }

    public Long getId() {
        return this.id;
    }

    public InventoryRequest getInventoryRequest() {
        return this.inventoryRequest;
    }

    public InventoryItem getInventoryItem() {
        return this.inventoryItem;
    }

    public double getRequestQuantity() {
        return this.requestQuantity;
    }

    public double getDeliveredQuantity() {
        return this.deliveredQuantity;
    }

    public double getPendingQuantity() {
        return this.pendingQuantity;
    }

    public LocalDateTime getDueDate() {
        return this.dueDate;
    }

    public LocalDateTime getDeliveredDate() {
        return this.deliveredDate;
    }

    public Unit getUnit() {
        return this.unit;
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

    public void setInventoryRequest(InventoryRequest inventoryRequest) {
        this.inventoryRequest = inventoryRequest;
    }

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public void setRequestQuantity(double requestQuantity) {
        this.requestQuantity = requestQuantity;
    }

    public void setDeliveredQuantity(double deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }

    public void setPendingQuantity(double pendingQuantity) {
        this.pendingQuantity = pendingQuantity;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public void setDeliveredDate(LocalDateTime deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventoryRequestItem)) return false;
        InventoryRequestItem requestItem = (InventoryRequestItem) o;
        return Double.compare(requestItem.requestQuantity, requestQuantity) == 0 &&
                Double.compare(requestItem.deliveredQuantity, deliveredQuantity) == 0 &&
                Double.compare(requestItem.pendingQuantity, pendingQuantity) == 0 &&
                Objects.equals(id, requestItem.id) &&
                Objects.equals(inventoryRequest, requestItem.inventoryRequest) &&
                Objects.equals(inventoryItem, requestItem.inventoryItem) &&
                Objects.equals(dueDate, requestItem.dueDate) &&
                Objects.equals(deliveredDate, requestItem.deliveredDate) &&
                unit == requestItem.unit &&
                status == requestItem.status &&
                Objects.equals(createdDate, requestItem.createdDate) &&
                Objects.equals(lastModifiedDate, requestItem.lastModifiedDate) &&
                Objects.equals(createdBy, requestItem.createdBy) &&
                Objects.equals(lastModifiedBy, requestItem.lastModifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, inventoryRequest, inventoryItem, requestQuantity, deliveredQuantity, pendingQuantity, dueDate, deliveredDate, unit, status, createdDate, lastModifiedDate, createdBy, lastModifiedBy);
    }

    @Override
    public String toString() {
        return "InventoryRequestItem{" +
                "id=" + id +
                ", inventoryRequest=" + (inventoryRequest != null ? inventoryRequest.getId() : null) +
                ", inventoryItem=" + (inventoryItem != null ? inventoryItem.getId() : null) +
                ", requestQuantity=" + requestQuantity +
                ", deliveredQuantity=" + deliveredQuantity +
                ", pendingQuantity=" + pendingQuantity +
                ", dueDate=" + dueDate +
                ", deliveredDate=" + deliveredDate +
                ", unit=" + unit +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                '}';
    }
}
