package dev.ordy.erp.business.step_set;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stepsets")
class StepSetController {

    private final StepSetService stepSetService;

    StepSetController(StepSetService stepSetService) {
        this.stepSetService = stepSetService;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    List<StepSet> all() {
        return stepSetService.getAllStepSets();
    }
    // end::get-aggregate-root[]

    @PostMapping
    StepSet newStep(@RequestBody StepSet newStepSet) {
        return stepSetService.createStepSet(newStepSet.getName(), newStepSet.getRole(), newStepSet.getBusiness());
    }


    @GetMapping("/{id}")
    StepSet one(@PathVariable Long id) {
        return stepSetService.getStepSetById(id);
    }


    @DeleteMapping("/{id}")
    void deleteStep(@PathVariable Long id) {
        stepSetService.deleteStepSet(id);
    }
}
