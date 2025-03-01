package dev.ordy.erp.business.item_category;

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
@Table(name = "ITEM_CATEGORY")
@EntityListeners(AuditingEntityListener.class)
public class ItemCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private ItemCategory parentCategory;
    
    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;
    
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    ItemCategory() {}

    public ItemCategory(String name, String description, Business business, ItemCategory parentCategory) {
        this.name = name;
        this.description = description;
        this.business = business;
        this.parentCategory = parentCategory;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public ItemCategory getParentCategory() {
        return this.parentCategory;
    }

    public Business getBusiness() {
        return business;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setParentCategory(ItemCategory parentCategory) {
        this.parentCategory = parentCategory;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ItemCategory))
            return false;
        ItemCategory category = (ItemCategory) o;
        return Objects.equals(this.id, category.id) && Objects.equals(this.name, category.name)
                && Objects.equals(this.description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.description);
    }

    @Override
    public String toString() {
        return "ItemCategory{" + "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", parentCategoryId=" + (parentCategory != null ? parentCategory.getId() : null) +
                ", businessId=" + (business != null ? business.getId() : null) +
                ", businessName=" + (business != null ? business.getName() : null) +
                '}';
    }
}
