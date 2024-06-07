package dev.ordy.erp.business.business;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
