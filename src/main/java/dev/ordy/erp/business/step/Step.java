package dev.ordy.erp.business.step;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.business.step_set.StepSet;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "STEP")
@EntityListeners(AuditingEntityListener.class)
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String role;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToOne
    @JoinColumn(name = "stepSet_id", nullable = false)
    private StepSet stepSet;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    Step() {}

    public Step(String name, String role, Business business,StepSet stepSet) {
        this.name = name;
        this.role = role;
        this.business = business;
        this.stepSet=stepSet;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getRole() {
        return this.role;
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

    public Business getBusiness() {
        return business;
    }

    public StepSet getStepSet() { return  stepSet;}

    public void setBusiness(Business business) {
        this.business = business;
    }

    public void setStepSet(StepSet stepSet) {this.stepSet = stepSet; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }




    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.role);
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", businessId=" + (business != null ? business.getId() : null) +
                ", businessName=" + (business != null ? business.getName() : null) +
                '}';
    }
}
