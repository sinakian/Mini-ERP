package dev.ordy.erp.inventory.inventoryItem;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryItemService {

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
        return inventoryItemRepository.save(inventoryItem);
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
