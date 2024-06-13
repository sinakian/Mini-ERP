package dev.ordy.erp.business.business;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.ordy.erp.common.Currency;
import dev.ordy.erp.inventory.inventory.Inventory;
import jakarta.persistence.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "BUSINESS")
@EntityListeners(AuditingEntityListener.class)
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
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




    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;



    Business() {}

    public Business(String name, String role,Currency currency,Inventory defaultMaterialInventory,Inventory defaultProductInventory) {

        this.name = name;
        this.role = role;
        this.currency=currency;
        this.defaultMaterialInventory=defaultMaterialInventory;
        this.defaultProductInventory=defaultProductInventory;
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

    public Currency getCurrency() {
        return this.currency;
    }

    public Inventory getDefaultProductInventory() {
        return defaultProductInventory;
    }
    public Inventory getDefaultMaterialInventory() {
        return defaultMaterialInventory;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    public void setDefaultProductInventory(Inventory defaultProductInventory) {
        this.defaultProductInventory = defaultProductInventory;
    }
    public void setDefaultMaterialInventory(Inventory defaultMaterialInventory) {
        this.defaultMaterialInventory = defaultMaterialInventory;
    }


    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Business))
            return false;
        Business business = (Business) o;
        return Objects.equals(this.id, business.id) && Objects.equals(this.name, business.name)
                && Objects.equals(this.role, business.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.role);
    }

    @Override
    public String toString() {
        return "Business{" + "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", role='" + this.role + '\'' +
                ", default product inventory='" + this.defaultProductInventory +
                ", default material inventory='" + this.defaultMaterialInventory +
                '}';
    }
}
