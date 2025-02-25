package dev.ordy.erp.business.business;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dev.ordy.erp.business.step_set.StepSet;

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
            business.setRole(updatedBusiness.getRole());

            return businessRepository.save(business);
        }).orElseThrow(() -> new RuntimeException("BusinessSettings not found with id " + id));
    }
}
