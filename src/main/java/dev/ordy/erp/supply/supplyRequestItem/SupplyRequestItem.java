package dev.ordy.erp.supply.supplyRequestItem;

import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.supply.supplyRequest.SupplyRequest;
import dev.ordy.erp.business.item.Item;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "SUPPLY_REQUEST_ITEM")
@EntityListeners(AuditingEntityListener.class)
public class SupplyRequestItem {

    public enum Unit {
        UNIT, KILOGRAM, LITER, PIECE
    }

    public enum Status {
        PENDING, DELIVERED, CANCELED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supply_request_id", nullable = false)
    private SupplyRequest supplyRequest;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    private double requestQuantity;
    private double suppliedQuantity;
    private double pendingQuantity;
    private double canceledQuantity;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    private LocalDateTime dueDate;

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

    public SupplyRequestItem() {}

    public SupplyRequestItem(SupplyRequest supplyRequest, Business business, Item item, double requestQuantity, double suppliedQuantity, double pendingQuantity, double canceledQuantity, Unit unit, LocalDateTime dueDate, Status status, String createdBy) {
        this.supplyRequest = supplyRequest;
        this.business = business;
        this.item = item;
        this.requestQuantity = requestQuantity;
        this.suppliedQuantity = suppliedQuantity;
        this.pendingQuantity = pendingQuantity;
        this.canceledQuantity = canceledQuantity;
        this.unit = unit;
        this.dueDate = dueDate;
        this.status = status;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public SupplyRequest getSupplyRequest() {
        return supplyRequest;
    }

    public Business getBusiness() {
        return business;
    }

    public Item getItem() {
        return item;
    }

    public double getRequestQuantity() {
        return requestQuantity;
    }

    public double getSuppliedQuantity() {
        return suppliedQuantity;
    }

    public double getPendingQuantity() {
        return pendingQuantity;
    }

    public double getCanceledQuantity() {
        return canceledQuantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public Status getStatus() {
        return status;
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

    public void setSupplyRequest(SupplyRequest supplyRequest) {
        this.supplyRequest = supplyRequest;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setRequestQuantity(double requestQuantity) {
        this.requestQuantity = requestQuantity;
    }

    public void setSuppliedQuantity(double suppliedQuantity) {
        this.suppliedQuantity = suppliedQuantity;
    }

    public void setPendingQuantity(double pendingQuantity) {
        this.pendingQuantity = pendingQuantity;
    }

    public void setCanceledQuantity(double canceledQuantity) {
        this.canceledQuantity = canceledQuantity;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplyRequestItem)) return false;
        SupplyRequestItem that = (SupplyRequestItem) o;
        return Double.compare(that.requestQuantity, requestQuantity) == 0 && Double.compare(that.suppliedQuantity, suppliedQuantity) == 0 && Double.compare(that.pendingQuantity, pendingQuantity) == 0 && Double.compare(that.canceledQuantity, canceledQuantity) == 0 && Objects.equals(id, that.id) && Objects.equals(supplyRequest, that.supplyRequest) && Objects.equals(business, that.business) && Objects.equals(item, that.item) && unit == that.unit && Objects.equals(dueDate, that.dueDate) && status == that.status && Objects.equals(createdDate, that.createdDate) && Objects.equals(lastModifiedDate, that.lastModifiedDate) && Objects.equals(createdBy, that.createdBy) && Objects.equals(lastModifiedBy, that.lastModifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, supplyRequest, business, item, requestQuantity, suppliedQuantity, pendingQuantity, canceledQuantity, unit, dueDate, status, createdDate, lastModifiedDate, createdBy, lastModifiedBy);
    }

    @Override
    public String toString() {
        return "SupplyRequestItem{" +
                "id=" + id +
                ", supplyRequest=" + supplyRequest +
                ", business=" + business +
                ", item=" + item +
                ", requestQuantity=" + requestQuantity +
                ", suppliedQuantity=" + suppliedQuantity +
                ", pendingQuantity=" + pendingQuantity +
                ", canceledQuantity=" + canceledQuantity +
                ", unit=" + unit +
                ", dueDate=" + dueDate +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                '}';
    }
}
