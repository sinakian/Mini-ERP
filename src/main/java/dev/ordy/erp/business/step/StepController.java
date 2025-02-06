package dev.ordy.erp.business.step;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/steps")
class StepController {

    private final StepService stepService;

    StepController(StepService stepService) {
        this.stepService = stepService;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    List<Step> all() {
        return stepService.getAllSteps();
    }
    // end::get-aggregate-root[]

    @PostMapping
    Step newStep(@RequestBody Step newStep) {
        return stepService.createStep(newStep.getName(), newStep.getRole(), newStep.getBusiness());
    }


    @GetMapping("/{id}")
    Step one(@PathVariable Long id) {
        return stepService.getStepById(id);
    }

    @PutMapping("/{id}")
    Step replaceStep(@RequestBody Step newStep, @PathVariable Long id) {
        return stepService.updateStep(id, newStep.getName(), newStep.getRole());
    }

    @DeleteMapping("/{id}")
    void deleteStep(@PathVariable Long id) {
        stepService.deleteStep(id);
    }
}
