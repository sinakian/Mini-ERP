package dev.ordy.erp.business.item_category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {
    List<ItemCategory> findByBusinessId(Long businessId);
    List<ItemCategory> findByBusinessIdAndParentCategoryIsNull(Long businessId);
    List<ItemCategory> findByParentCategoryId(Long parentCategoryId);
    Optional<ItemCategory> findByNameAndBusinessId(String name, Long businessId);
}