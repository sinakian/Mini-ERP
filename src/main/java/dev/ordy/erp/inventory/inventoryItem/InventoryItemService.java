package dev.ordy.erp.inventory.inventoryItem;

import dev.ordy.erp.inventory.inventoryTransaction.InventoryTransaction;
import dev.ordy.erp.inventory.inventoryTransaction.InventoryTransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Service
public class InventoryItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryItemService.class);
    private final InventoryItemRepository inventoryItemRepository;


    public InventoryItemService(InventoryItemRepository inventoryItemRepository, InventoryTransactionService inventoryTransactionService) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    @Transactional(readOnly = true)
    public List<InventoryItem> getAllInventoryItems() {
        return inventoryItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public InventoryItem getInventoryItemById(Long id) {
        return inventoryItemRepository.findById(id)
                .orElseThrow(() -> new InventoryItemNotFoundException(id));
    }


    @Transactional(readOnly = true)
    public double getAvailableQuantity(Long inventoryItemId) {
        InventoryItem inventoryItem = inventoryItemRepository.findById(inventoryItemId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory item not found"));
        return inventoryItem.getAvailableQuantity();
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

    @Transactional
    public InventoryItem updateAvailableBalance(Long id, double newAvailableBalance) {
        return inventoryItemRepository.findById(id)
                .map(inventoryItem -> {
                    inventoryItem.setAvailableQuantity(newAvailableBalance);
                    return inventoryItemRepository.save(inventoryItem);
                })
                .orElseThrow(() -> new InventoryItemNotFoundException(id));
    }

    @Transactional
    public InventoryItem changeBalance(Long inventoryItemId, InventoryTransaction transaction) {
        double newBalance=transaction.getNewBalance();
        InventoryItem inventoryItem =transaction.getInventoryItem();
        inventoryItem.setQuantity(newBalance);
        return inventoryItemRepository.save(inventoryItem);
    }






}
