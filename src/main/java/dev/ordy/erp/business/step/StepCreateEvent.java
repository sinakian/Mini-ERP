package dev.ordy.erp.business.step;


import org.springframework.context.ApplicationEvent;

public class StepCreateEvent extends ApplicationEvent {
    private final Step step;

    public StepCreateEvent(Object source, Step step) {
        super(source);
        this.step = step;
    }

    public Step getStep() {
        return step;
    }
}
