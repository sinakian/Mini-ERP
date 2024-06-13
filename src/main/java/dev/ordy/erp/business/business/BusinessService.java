package dev.ordy.erp.business.business;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final ApplicationEventPublisher eventPublisher;

    public BusinessService(BusinessRepository businessRepository, ApplicationEventPublisher eventPublisher) {
        this.businessRepository = businessRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Business createBusiness(Business business) {
        Business savedBusiness = businessRepository.save(business);
        eventPublisher.publishEvent(new BusinessCreateEvent(this, savedBusiness));
        return savedBusiness;
    }

    public Optional<Business> getBusinessById(Long id) {
        return businessRepository.findById(id);
    }

    public void updateBusiness(Long id, Business updatedBusiness) {
        businessRepository.findById(id).map(business -> {
            business.setName(updatedBusiness.getName());
            business.setCurrency(updatedBusiness.getCurrency());
            business.setDefaultProductInventory(updatedBusiness.getDefaultProductInventory());
            business.setDefaultMaterialInventory(updatedBusiness.getDefaultMaterialInventory());
            business.setRole(updatedBusiness.getRole());

            return businessRepository.save(business);
        }).orElseThrow(() -> new RuntimeException("Business not found with id " + id));
    }
}
