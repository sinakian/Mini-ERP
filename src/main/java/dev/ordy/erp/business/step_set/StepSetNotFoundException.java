package dev.ordy.erp.business.step_set;

class StepSetNotFoundException extends RuntimeException {

    StepSetNotFoundException(Long id) {
        super("Could not find  stepset " + id);
    }
}
