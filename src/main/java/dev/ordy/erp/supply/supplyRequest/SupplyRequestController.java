package dev.ordy.erp.supply.supplyRequest;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supply-requests")
public class SupplyRequestController {

    private final SupplyRequestService supplyRequestService;

    public SupplyRequestController(SupplyRequestService supplyRequestService) {
        this.supplyRequestService = supplyRequestService;
    }

    @GetMapping
    public List<SupplyRequest> all() {
        return supplyRequestService.getAllSupplyRequests();
    }

    @PostMapping
    public SupplyRequest newSupplyRequest(@RequestBody SupplyRequest newSupplyRequest) {
        return supplyRequestService.createSupplyRequest(newSupplyRequest);
    }

    @GetMapping("/{id}")
    public SupplyRequest one(@PathVariable Long id) {
        return supplyRequestService.getSupplyRequestById(id)
                .orElseThrow(() -> new SupplyRequestNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public void deleteSupplyRequest(@PathVariable Long id) {
        supplyRequestService.deleteSupplyRequest(id);
    }
}
