package dev.ordy.erp.inventory.inventoryRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryRequestService {

    private final InventoryRequestRepository inventoryRequestRepository;

    public InventoryRequestService(InventoryRequestRepository inventoryRequestRepository) {
        this.inventoryRequestRepository = inventoryRequestRepository;
    }

    @Transactional(readOnly = true)
    public List<InventoryRequest> getAllInventoryRequests() {
        return inventoryRequestRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<InventoryRequest> getInventoryRequestById(Long id) {
        return inventoryRequestRepository.findById(id);
    }

    @Transactional
    public InventoryRequest createInventoryRequest(InventoryRequest inventoryRequest) {
        return inventoryRequestRepository.save(inventoryRequest);
    }

    @Transactional
    public InventoryRequest updateInventoryRequest(Long id, InventoryRequest newInventoryRequest) {
        return inventoryRequestRepository.findById(id)
                .map(inventoryRequest -> {
                    inventoryRequest.setBusiness(newInventoryRequest.getBusiness());
                    inventoryRequest.setInventory(newInventoryRequest.getInventory());
                    inventoryRequest.setQuantity(newInventoryRequest.getQuantity());
                    inventoryRequest.setDueDate(newInventoryRequest.getDueDate());
                    inventoryRequest.setDeliveredDate(newInventoryRequest.getDeliveredDate());
                    inventoryRequest.setReferenceType(newInventoryRequest.getReferenceType());
                    inventoryRequest.setReferenceId(newInventoryRequest.getReferenceId());
                    inventoryRequest.setStatus(newInventoryRequest.getStatus());
                    return inventoryRequestRepository.save(inventoryRequest);
                })
                .orElseThrow(() -> new InventoryRequestNotFoundException(id));
    }

    @Transactional
    public void deleteInventoryRequest(Long id) {
        inventoryRequestRepository.deleteById(id);
    }

    @Transactional
    public InventoryRequest updateInventoryRequestStatus(Long id, InventoryRequest.Status newStatus) {
        return inventoryRequestRepository.findById(id)
                .map(inventoryRequest -> {
                    inventoryRequest.setStatus(newStatus);
                    return inventoryRequestRepository.save(inventoryRequest);
                })
                .orElseThrow(() -> new InventoryRequestNotFoundException(id));
    }


}
