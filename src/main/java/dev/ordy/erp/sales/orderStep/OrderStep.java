package dev.ordy.erp.sales.orderStep;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.sales.order.Order;
import dev.ordy.erp.business.step.Step;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "ORDER_STEP")
@EntityListeners(AuditingEntityListener.class)
public class OrderStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "step_id", nullable = false)
    private Step step;

    private boolean isCompleted;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    public OrderStep() {}

    public OrderStep(Business business,
                     Order order,
                     Step step,
                     boolean isCompleted,
                     String createdBy
    ) {
        this.business = business;
        this.order = order;
        this.step = step;
        this.isCompleted = isCompleted;
        this.createdBy = createdBy;

    }



    public Long getId() {
        return id;
    }

    public Business getBusiness() {
        return business;
    }

    public Order getOrder() {
        return order;
    }

    public Step getStep() {
        return step;
    }

    public Boolean getIscompleted(){
        return isCompleted;
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


    public void setBusiness(Business business) { this.business = business;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setStep(Step step) {
        this.step=step;
    }

    public void setIsCompleted(boolean isCompleted){
        this.isCompleted=isCompleted;
    }
}
