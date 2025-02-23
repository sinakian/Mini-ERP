package dev.ordy.erp.business.step;

import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.business.step_set.StepSet;
import dev.ordy.erp.sales.orderItem.OrderItem;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StepService {

    private final StepRepository stepRepository;
    private final ApplicationEventPublisher eventPublisher;

    public StepService(StepRepository stepRepository, ApplicationEventPublisher eventPublisher) {
        this.stepRepository = stepRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Step createStep(String name, String role, Business business, StepSet stepSet) {
        Step step = new Step(name, role, business,stepSet);
        stepRepository.save(step);
        eventPublisher.publishEvent(new StepCreateEvent(this,step));
        return step;
    }



    @Transactional
    public void deleteStep(Long stepId) {
        stepRepository.deleteById(stepId);
    }

    @Transactional
    public Step updateStep(Long stepId, String name, String role) {
        Optional<Step> optionalstep = stepRepository.findById(stepId);
        if (optionalstep.isPresent()) {
            Step step = optionalstep.get();
            step.setName(name);
            step.setRole(role);
            return stepRepository.save(step);
        } else {
            // Handle step not found error
            throw new RuntimeException("step not found with id: " + stepId);
        }
    }

    @Transactional(readOnly = true)
    public Step getStepById(Long stepId) {
        return stepRepository.findById(stepId)
                .orElseThrow(() -> new RuntimeException("step not found with id: " + stepId));
    }

    @Transactional(readOnly = true)
    public List<Step> getAllSteps() {
        return stepRepository.findAll();
    }


    public List<Step> getStepsByBusinessId(Long businessId) {
        return stepRepository.findByBusinessId(businessId);
    }

    public List<Step> getStepsByStepSetId(Long stepSetId) {
        return stepRepository.findByStepSetId(stepSetId);
    }
}
