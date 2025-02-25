package dev.ordy.erp.business.business_settings;

import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.business.business.BusinessService;
import dev.ordy.erp.business.step_set.StepSet;
import dev.ordy.erp.inventory.inventory.Inventory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BusinessSettingsService {

    private final BusinessSettingsRepository businessSettingsRepository;
    private final BusinessService businessService;

    public BusinessSettingsService(BusinessSettingsRepository businessSettingsRepository,
                                   ApplicationEventPublisher eventPublisher,
                                   BusinessService businessService
    ) {
        this.businessSettingsRepository = businessSettingsRepository;
        this.businessService = businessService;
    }

    @Transactional
    public BusinessSettings createBusinessSettings(Long businessId, BusinessSettings businessSettings) {
        Optional<Business> businessOptional = businessService.getBusinessById(businessId);
        if (businessOptional.isEmpty()) {
            throw new IllegalArgumentException("Business with id " + businessId + " not found");
        }

        Business business = businessOptional.get();
        businessSettings.setBusiness(business);

        return businessSettingsRepository.save(businessSettings);
    }

    @Transactional
    public BusinessSettings setDefaultStepset(Long businessId, StepSet stepSet) {
        Optional<BusinessSettings> businessSettingsOptional = businessSettingsRepository.findByBusinessId(businessId);
        if (businessSettingsOptional.isEmpty()) {
            throw new IllegalArgumentException("BusinessSettings for business id " + businessId + " not found");
        }

        BusinessSettings businessSettings = businessSettingsOptional.get();
        businessSettings.setDefaultStepSet(stepSet);
        return businessSettingsRepository.save(businessSettings);
    }

    @Transactional
    public BusinessSettings setDefaultProductInventory(Long businessId, Inventory productInventory) {
        Optional<BusinessSettings> businessSettingsOptional = businessSettingsRepository.findByBusinessId(businessId);
        if (businessSettingsOptional.isEmpty()) {
            throw new IllegalArgumentException("BusinessSettings for business id " + businessId + " not found");
        }

        BusinessSettings businessSettings = businessSettingsOptional.get();
        businessSettings.setDefaultProductInventory(productInventory);
        return businessSettingsRepository.save(businessSettings);
    }

    @Transactional
    public BusinessSettings setDefaultMaterialInventory(Long businessId, Inventory materialInventory) {
        Optional<BusinessSettings> businessSettingsOptional = businessSettingsRepository.findByBusinessId(businessId);
        if (businessSettingsOptional.isEmpty()) {
            throw new IllegalArgumentException("BusinessSettings for business id " + businessId + " not found");
        }

        BusinessSettings businessSettings = businessSettingsOptional.get();
        businessSettings.setDefaultMaterialInventory(materialInventory);
        return businessSettingsRepository.save(businessSettings);
    }

    @Transactional(readOnly = true)
    public BusinessSettings getDefaultSettings(Long businessId) {
        return businessSettingsRepository.findByBusinessId(businessId)
                .orElseThrow(() -> new IllegalArgumentException("BusinessSettings for business id " + businessId + " not found"));
    }



}
