package dev.ordy.erp.inventory.inventoryTransaction;

import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.inventory.inventory.Inventory;
import dev.ordy.erp.inventory.inventoryItem.InventoryItem;
import dev.ordy.erp.inventory.inventoryReceipt.InventoryReceipt;
import dev.ordy.erp.inventory.inventoryReceiptItem.InventoryReceiptItem;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "INVENTORY_TRANSACTION")
@EntityListeners(AuditingEntityListener.class)
public class InventoryTransaction {

    public enum TransactionType {
        IN, OUT
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

    @ManyToOne
    @JoinColumn(name = "inventory_item_id", nullable = false)
    private InventoryItem inventoryItem;

    private double quantity;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "inventory_receipt_id")
    private InventoryReceipt inventoryReceipt;

    @ManyToOne
    @JoinColumn(name = "inventory_receipt_item_id")
    private InventoryReceiptItem inventoryReceiptItem;

    private double oldBalance;
    private double newBalance;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    InventoryTransaction() {}

    public InventoryTransaction(Business business, Inventory inventory, InventoryItem inventoryItem, double quantity, TransactionType transactionType, InventoryReceipt inventoryReceipt, InventoryReceiptItem inventoryReceiptItem, double oldBalance, double newBalance) {
        this.business = business;
        this.inventory = inventory;
        this.inventoryItem = inventoryItem;
        this.quantity = quantity;
        this.transactionType = transactionType;
        this.inventoryReceipt = inventoryReceipt;
        this.inventoryReceiptItem = inventoryReceiptItem;
        this.oldBalance = oldBalance;
        this.newBalance = newBalance;
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

    public InventoryItem getInventoryItem() {
        return this.inventoryItem;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public TransactionType getTransactionType() {
        return this.transactionType;
    }

    public InventoryReceipt getInventoryReceipt() {
        return this.inventoryReceipt;
    }

    public InventoryReceiptItem getInventoryReceiptItem() {
        return this.inventoryReceiptItem;
    }

    public double getOldBalance() {
        return this.oldBalance;
    }

    public double getNewBalance() {
        return this.newBalance;
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

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setInventoryReceipt(InventoryReceipt inventoryReceipt) {
        this.inventoryReceipt = inventoryReceipt;
    }

    public void setInventoryReceiptItem(InventoryReceiptItem inventoryReceiptItem) {
        this.inventoryReceiptItem = inventoryReceiptItem;
    }

    public void setOldBalance(double oldBalance) {
        this.oldBalance = oldBalance;
    }

    public void setNewBalance(double newBalance) {
        this.newBalance = newBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventoryTransaction)) return false;
        InventoryTransaction transaction = (InventoryTransaction) o;
        return Double.compare(transaction.quantity, quantity) == 0 &&
                Double.compare(transaction.oldBalance, oldBalance) == 0 &&
                Double.compare(transaction.newBalance, newBalance) == 0 &&
                Objects.equals(id, transaction.id) &&
                Objects.equals(business, transaction.business) &&
                Objects.equals(inventory, transaction.inventory) &&
                Objects.equals(inventoryItem, transaction.inventoryItem) &&
                transactionType == transaction.transactionType &&
                Objects.equals(inventoryReceipt, transaction.inventoryReceipt) &&
                Objects.equals(inventoryReceiptItem, transaction.inventoryReceiptItem) &&
                Objects.equals(createdDate, transaction.createdDate) &&
                Objects.equals(lastModifiedDate, transaction.lastModifiedDate) &&
                Objects.equals(createdBy, transaction.createdBy) &&
                Objects.equals(lastModifiedBy, transaction.lastModifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, business, inventory, inventoryItem, quantity, transactionType, inventoryReceipt, inventoryReceiptItem, oldBalance, newBalance, createdDate, lastModifiedDate, createdBy, lastModifiedBy);
    }

    @Override
    public String toString() {
        return "InventoryTransaction{" +
                "id=" + id +
                ", business=" + (business != null ? business.getId() : null) +
                ", inventory=" + (inventory != null ? inventory.getId() : null) +
                ", inventoryItem=" + (inventoryItem != null ? inventoryItem.getId() : null) +
                ", quantity=" + quantity +
                ", transactionType=" + transactionType +
                ", inventoryReceipt=" + (inventoryReceipt != null ? inventoryReceipt.getId() : null) +
                ", inventoryReceiptItem=" + (inventoryReceiptItem != null ? inventoryReceiptItem.getId() : null) +
                ", oldBalance=" + oldBalance +
                ", newBalance=" + newBalance +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                '}';
    }
}
