package dev.ordy.erp.inventory.inventoryRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory-requests")
public class InventoryRequestController {

    private final InventoryRequestService inventoryRequestService;

    public InventoryRequestController(InventoryRequestService inventoryRequestService) {
        this.inventoryRequestService = inventoryRequestService;
    }

    @GetMapping
    public List<InventoryRequest> all() {
        return inventoryRequestService.getAllInventoryRequests();
    }

    @PostMapping
    public InventoryRequest newInventoryRequest(@RequestBody InventoryRequest newInventoryRequest) {
        return inventoryRequestService.createInventoryRequest(newInventoryRequest);
    }

    @GetMapping("/{id}")
    public InventoryRequest one(@PathVariable Long id) {
        return inventoryRequestService.getInventoryRequestById(id)
                .orElseThrow(() -> new InventoryRequestNotFoundException(id));
    }

    @PutMapping("/{id}")
    public InventoryRequest replaceInventoryRequest(@RequestBody InventoryRequest newInventoryRequest, @PathVariable Long id) {
        return inventoryRequestService.updateInventoryRequest(id, newInventoryRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteInventoryRequest(@PathVariable Long id) {
        inventoryRequestService.deleteInventoryRequest(id);
    }

    @PatchMapping("{id}/status")
    public ResponseEntity<InventoryRequest> updateInventoryRequestStatus(@PathVariable Long id, @RequestParam InventoryRequest.Status status) {
        InventoryRequest updatedInventoryRequest = inventoryRequestService.updateInventoryRequestStatus(id, status);
        return ResponseEntity.ok(updatedInventoryRequest);
    }

}
