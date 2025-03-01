package dev.ordy.erp.business.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.ordy.erp.business.item.enums.InventoryPolicy;
import dev.ordy.erp.business.item.enums.ItemType;
import dev.ordy.erp.common.Unit;
import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.business.item_category.ItemCategory;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "ITEM")
@EntityListeners(AuditingEntityListener.class)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String role;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ItemCategory category;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    @Enumerated(EnumType.STRING)
    private InventoryPolicy inventoryPolicy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    Item() {}

    public Item(String name, String role, Business business, InventoryPolicy inventoryPolicy, ItemType itemType, Unit unit, ItemCategory category) {
        this.name = name;
        this.role = role;
        this.business = business;
        this.inventoryPolicy = inventoryPolicy;
        this.itemType = itemType;
        this.unit = unit;
        this.category = category;
    }

    public Item(String name, String role, Business business, InventoryPolicy inventoryPolicy, ItemType itemType, Unit unit) {
        this.name = name;
        this.role = role;
        this.business = business;
        this.inventoryPolicy = inventoryPolicy;
        this.itemType = itemType;
        this.unit = unit;
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

    public ItemCategory getCategory() {
        return category;
    }

    public InventoryPolicy getInventoryPolicy(){
        return this.inventoryPolicy;
    }

    public ItemType getItemType(){
        return this.itemType;
    }

    public Unit getUnit(){return this.unit;}

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public void setCategory(ItemCategory category) {
        this.category = category;
    }

    public void setInventoryPolicy(InventoryPolicy inventoryPolicy){
        this.inventoryPolicy = inventoryPolicy;
    }

    public void setItemType(ItemType itemType){
        this.itemType = itemType;
    }

    public void setUnit(Unit unit){
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Item))
            return false;
        Item item = (Item) o;
        return Objects.equals(this.id, item.id) && Objects.equals(this.name, item.name)
                && Objects.equals(this.role, item.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.role);
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id +
                ", inventoryPolicy=" + inventoryPolicy +
                ", itemType=" + itemType +
                ", unit='" + unit +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", businessId=" + (business != null ? business.getId() : null) +
                ", businessName=" + (business != null ? business.getName() : null) +
                ", categoryId=" + (category != null ? category.getId() : null) +
                ", categoryName=" + (category != null ? category.getName() : null) +
                '}';
    }
}