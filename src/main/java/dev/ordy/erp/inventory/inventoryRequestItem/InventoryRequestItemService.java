package dev.ordy.erp.inventory.inventoryRequestItem;

import dev.ordy.erp.inventory.inventoryRequest.InventoryRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryRequestItemService {

    private final InventoryRequestItemRepository inventoryRequestItemRepository;

    public InventoryRequestItemService(InventoryRequestItemRepository inventoryRequestItemRepository) {
        this.inventoryRequestItemRepository = inventoryRequestItemRepository;
    }

    @Transactional(readOnly = true)
    public List<InventoryRequestItem> getAllInventoryRequestItems() {
        return inventoryRequestItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public InventoryRequestItem getInventoryRequestItemById(Long id) {
        return inventoryRequestItemRepository.findById(id)
                .orElseThrow(() -> new InventoryRequestItemNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<InventoryRequestItem> getItemsByRequest(InventoryRequest inventoryRequest) {
        return inventoryRequestItemRepository.findByInventoryRequest(inventoryRequest);
    }

    @Transactional
    public InventoryRequestItem createInventoryRequestItem(InventoryRequestItem inventoryRequestItem) {
        return inventoryRequestItemRepository.save(inventoryRequestItem);
    }

    @Transactional
    public InventoryRequestItem updateInventoryRequestItem(Long id, InventoryRequestItem newInventoryRequestItem) {
        return inventoryRequestItemRepository.findById(id)
                .map(inventoryRequestItem -> {
                    inventoryRequestItem.setInventoryRequest(newInventoryRequestItem.getInventoryRequest());
                    inventoryRequestItem.setInventoryItem(newInventoryRequestItem.getInventoryItem());
                    inventoryRequestItem.setRequestQuantity(newInventoryRequestItem.getRequestQuantity());
                    inventoryRequestItem.setDeliveredQuantity(newInventoryRequestItem.getDeliveredQuantity());
                    inventoryRequestItem.setPendingQuantity(newInventoryRequestItem.getPendingQuantity());
                    inventoryRequestItem.setDueDate(newInventoryRequestItem.getDueDate());
                    inventoryRequestItem.setDeliveredDate(newInventoryRequestItem.getDeliveredDate());
                    inventoryRequestItem.setUnit(newInventoryRequestItem.getUnit());
                    inventoryRequestItem.setStatus(newInventoryRequestItem.getStatus());
                    return inventoryRequestItemRepository.save(inventoryRequestItem);
                })
                .orElseThrow(() -> new InventoryRequestItemNotFoundException(id));
    }

    @Transactional
    public void deleteInventoryRequestItem(Long id) {
        inventoryRequestItemRepository.deleteById(id);
    }
}
