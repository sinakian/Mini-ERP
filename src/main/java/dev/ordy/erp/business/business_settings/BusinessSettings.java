package dev.ordy.erp.business.business_settings;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.business.step_set.StepSet;
import dev.ordy.erp.common.Currency;
import dev.ordy.erp.inventory.inventory.Inventory;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Table(name = "BUSINESS_SETTINGS")
@EntityListeners(AuditingEntityListener.class)
public class BusinessSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    private String role;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_inventory_id", nullable = true)
    @JsonManagedReference
    private Inventory defaultProductInventory;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "material_inventory_id", nullable = true)
    @JsonManagedReference
    private Inventory defaultMaterialInventory;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "step_set_id", nullable = false)
    @JsonManagedReference
    private StepSet defaultStepSet;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;



    BusinessSettings() {}

    public BusinessSettings(String name,
                            String role,
                            Currency currency,
                            Inventory defaultMaterialInventory,
                            Inventory defaultProductInventory,
                            StepSet defaultStepSet
    ) {

        this.role = role;
        this.currency=currency;
        this.defaultMaterialInventory=defaultMaterialInventory;
        this.defaultProductInventory=defaultProductInventory;
        this.defaultStepSet=defaultStepSet;
    }

    public Long getId() {
        return this.id;
    }
    public String getRole() {
        return this.role;
    }
    public Currency getCurrency() {
        return this.currency;
    }
    public Inventory getDefaultProductInventory() {
        return defaultProductInventory;
    }
    public Inventory getDefaultMaterialInventory() {
        return defaultMaterialInventory;
    }
    public StepSet getDefaultStepSet() {return defaultStepSet;}
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
    public void setRole(String role) {
        this.role = role;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    public void setBusiness(Business business) {
        this.business = business;
    }

    public void setDefaultProductInventory(Inventory defaultProductInventory) {
        this.defaultProductInventory = defaultProductInventory;
    }
    public void setDefaultMaterialInventory(Inventory defaultMaterialInventory) {
        this.defaultMaterialInventory = defaultMaterialInventory;
    }
    public void setDefaultStepSet(StepSet defaultStepSet){
        this.defaultStepSet = defaultStepSet;
    }


}
