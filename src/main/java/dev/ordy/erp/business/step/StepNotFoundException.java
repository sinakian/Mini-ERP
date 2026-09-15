package dev.ordy.erp.business.step;

class StepNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    StepNotFoundException(Long id) {
        super("Could not find  step " + id);
    }
}
