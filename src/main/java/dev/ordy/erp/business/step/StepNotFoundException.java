package dev.ordy.erp.business.step;

class StepNotFoundException extends RuntimeException {

    StepNotFoundException(Long id) {
        super("Could not find  step " + id);
    }
}
