package dev.ordy.erp.business.step;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepRepository extends JpaRepository<Step, Long> {

    List<Step> findByBusinessId(Long businessId);
    List<Step> findByStepSetId(Long stepSetId);
}
