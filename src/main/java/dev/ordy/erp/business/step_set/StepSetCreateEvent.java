package dev.ordy.erp.business.step_set;


import org.springframework.context.ApplicationEvent;

public class StepSetCreateEvent extends ApplicationEvent {
    private final StepSet stepSet;

    public StepSetCreateEvent(Object source, StepSet stepSet) {
        super(source);
        this.stepSet = stepSet;
    }

    public StepSet getStepSet() {
        return stepSet;
    }
}
