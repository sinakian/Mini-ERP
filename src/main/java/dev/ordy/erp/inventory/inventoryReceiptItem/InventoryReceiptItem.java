package dev.ordy.erp.inventory.inventoryReceiptItem;

import dev.ordy.erp.inventory.inventoryReceipt.InventoryReceipt;
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
@Table(name = "INVENTORY_RECEIPT_ITEM")
@EntityListeners(AuditingEntityListener.class)
public class InventoryReceiptItem {

    public enum Unit {
        PIECE, KILOGRAM, LITER // Example units, modify as needed
    }

    public enum Status {
        DELIVERED, PENDING, CANCELED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventory_receipt_id", nullable = false)
    private InventoryReceipt inventoryReceipt;

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

    InventoryReceiptItem() {}

    public InventoryReceiptItem(InventoryReceipt inventoryReceipt, InventoryItem inventoryItem, double requestQuantity, double deliveredQuantity, double pendingQuantity, LocalDateTime dueDate, LocalDateTime deliveredDate, Unit unit, Status status) {
        this.inventoryReceipt = inventoryReceipt;
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

    public InventoryReceipt getInventoryReceipt() {
        return this.inventoryReceipt;
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

    public void setInventoryReceipt(InventoryReceipt inventoryReceipt) {
        this.inventoryReceipt = inventoryReceipt;
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
        if (!(o instanceof InventoryReceiptItem)) return false;
        InventoryReceiptItem receiptItem = (InventoryReceiptItem) o;
        return Double.compare(receiptItem.requestQuantity, requestQuantity) == 0 &&
                Double.compare(receiptItem.deliveredQuantity, deliveredQuantity) == 0 &&
                Double.compare(receiptItem.pendingQuantity, pendingQuantity) == 0 &&
                Objects.equals(id, receiptItem.id) &&
                Objects.equals(inventoryReceipt, receiptItem.inventoryReceipt) &&
                Objects.equals(inventoryItem, receiptItem.inventoryItem) &&
                Objects.equals(dueDate, receiptItem.dueDate) &&
                Objects.equals(deliveredDate, receiptItem.deliveredDate) &&
                unit == receiptItem.unit &&
                status == receiptItem.status &&
                Objects.equals(createdDate, receiptItem.createdDate) &&
                Objects.equals(lastModifiedDate, receiptItem.lastModifiedDate) &&
                Objects.equals(createdBy, receiptItem.createdBy) &&
                Objects.equals(lastModifiedBy, receiptItem.lastModifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, inventoryReceipt, inventoryItem, requestQuantity, deliveredQuantity, pendingQuantity, dueDate, deliveredDate, unit, status, createdDate, lastModifiedDate, createdBy, lastModifiedBy);
    }

    @Override
    public String toString() {
        return "InventoryReceiptItem{" +
                "id=" + id +
                ", inventoryReceipt=" + (inventoryReceipt != null ? inventoryReceipt.getId() : null) +
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
