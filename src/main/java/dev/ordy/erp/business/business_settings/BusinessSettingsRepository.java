package dev.ordy.erp.business.business_settings;

import dev.ordy.erp.business.business.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BusinessSettingsRepository extends JpaRepository<BusinessSettings, Long> {
    Optional<BusinessSettings> findByBusinessId(Long businessId);
}
