package dev.ordy.erp.finance.tax;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaxRepository extends JpaRepository<Tax, Long> {
    List<Tax> findByBusinessId(String businessId);
    Optional<Tax> findByCodeAndBusinessId(String code, String businessId);
}