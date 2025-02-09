package dev.ordy.erp.business.step_set;

import dev.ordy.erp.business.business.Business;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StepSetService {

    private final StepSetRepository stepSetRepository;
    private final ApplicationEventPublisher eventPublisher;

    public StepSetService(StepSetRepository stepSetRepository, ApplicationEventPublisher eventPublisher) {
        this.stepSetRepository = stepSetRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public StepSet createStepSet(String name, String role, Business business) {
        StepSet stepSet = new StepSet(name, role, business);
        stepSetRepository.save(stepSet);
        eventPublisher.publishEvent(new StepSetCreateEvent(this, stepSet));
        return stepSet;
    }

    @Transactional
    public void deleteStepSet(Long stepId) {
        stepSetRepository.deleteById(stepId);
    }


    @Transactional(readOnly = true)
    public StepSet getStepSetById(Long stepId) {
        return stepSetRepository.findById(stepId)
                .orElseThrow(() -> new RuntimeException("step not found with id: " + stepId));
    }

    @Transactional(readOnly = true)
    public List<StepSet> getAllStepSets() {
        return stepSetRepository.findAll();
    }



}
