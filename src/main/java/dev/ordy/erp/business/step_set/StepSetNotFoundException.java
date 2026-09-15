package dev.ordy.erp.business.step_set;

class StepSetNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    StepSetNotFoundException(Long id) {
        super("Could not find  stepset " + id);
    }
}
