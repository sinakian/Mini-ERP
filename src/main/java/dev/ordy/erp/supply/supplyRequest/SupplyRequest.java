package dev.ordy.erp.supply.supplyRequest;

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
@Table(name = "SUPPLY_REQUEST")
@EntityListeners(AuditingEntityListener.class)
public class SupplyRequest {

    public enum ReferenceType {
        INVOICE, DIRECT
    }

    public enum Status {
        PENDING, SUPPLIED, CANCELED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    private double requestedQuantity;
    private double suppliedQuantity;
    private double pendingQuantity;
    private double canceledQuantity;

    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    private ReferenceType referenceType;

    private String referenceId;

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

    public SupplyRequest() {}

    public SupplyRequest(Business business, double requestedQuantity, double suppliedQuantity, double pendingQuantity, double canceledQuantity, LocalDateTime dueDate, ReferenceType referenceType, String referenceId, Status status, String createdBy) {
        this.business = business;
        this.requestedQuantity = requestedQuantity;
        this.suppliedQuantity = suppliedQuantity;
        this.pendingQuantity = pendingQuantity;
        this.canceledQuantity = canceledQuantity;
        this.dueDate = dueDate;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.status = status;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public Business getBusiness() {
        return business;
    }

    public double getRequestedQuantity() {
        return requestedQuantity;
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

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public ReferenceType getReferenceType() {
        return referenceType;
    }

    public String getReferenceId() {
        return referenceId;
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

    public void setBusiness(Business business) {
        this.business = business;
    }

    public void setRequestedQuantity(double requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
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

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public void setReferenceType(ReferenceType referenceType) {
        this.referenceType = referenceType;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplyRequest)) return false;
        SupplyRequest that = (SupplyRequest) o;
        return Double.compare(that.requestedQuantity, requestedQuantity) == 0 && Double.compare(that.suppliedQuantity, suppliedQuantity) == 0 && Double.compare(that.pendingQuantity, pendingQuantity) == 0 && Double.compare(that.canceledQuantity, canceledQuantity) == 0 && Objects.equals(id, that.id) && Objects.equals(business, that.business) && Objects.equals(dueDate, that.dueDate) && referenceType == that.referenceType && Objects.equals(referenceId, that.referenceId) && status == that.status && Objects.equals(createdDate, that.createdDate) && Objects.equals(lastModifiedDate, that.lastModifiedDate) && Objects.equals(createdBy, that.createdBy) && Objects.equals(lastModifiedBy, that.lastModifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, business, requestedQuantity, suppliedQuantity, pendingQuantity, canceledQuantity, dueDate, referenceType, referenceId, status, createdDate, lastModifiedDate, createdBy, lastModifiedBy);
    }

    @Override
    public String toString() {
        return "SupplyRequest{" +
                "id=" + id +
                ", business=" + business +
                ", requestedQuantity=" + requestedQuantity +
                ", suppliedQuantity=" + suppliedQuantity +
                ", pendingQuantity=" + pendingQuantity +
                ", canceledQuantity=" + canceledQuantity +
                ", dueDate=" + dueDate +
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
