package dev.ordy.erp.inventory.inventoryItem;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryItemService.class);
    private final InventoryItemRepository inventoryItemRepository;

    public InventoryItemService(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    @Transactional(readOnly = true)
    public List<InventoryItem> getAllInventoryItems() {
        return inventoryItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<InventoryItem> getInventoryItemById(Long id) {
        return inventoryItemRepository.findById(id);
    }

    @Transactional
    public InventoryItem createInventoryItem(InventoryItem inventoryItem) {
        try {
            return inventoryItemRepository.save(inventoryItem);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Error occurred while saving InventoryItem: {}", e.getMessage());
            // You can add additional logging or error handling here if needed
            throw e; // Re-throw the exception to propagate it up the call stack
        }
    }

    @Transactional
    public InventoryItem updateInventoryItem(Long id, InventoryItem newInventoryItem) {
        return inventoryItemRepository.findById(id)
                .map(inventoryItem -> {
                    inventoryItem.setName(newInventoryItem.getName());
                    inventoryItem.setQuantity(newInventoryItem.getQuantity());
                    return inventoryItemRepository.save(inventoryItem);
                })
                .orElseThrow(() -> new InventoryItemNotFoundException(id));
    }

    @Transactional
    public void deleteInventoryItem(Long id) {
        inventoryItemRepository.deleteById(id);
    }
}
