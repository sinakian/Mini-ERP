package dev.ordy.erp.inventory.inventory;

import dev.ordy.erp.business.business.Business;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(readOnly = true)
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Inventory> getInventoryById(Long id) {
        return inventoryRepository.findById(id);
    }

    @Transactional
    public Inventory createInventory(Business business, Inventory.InventoryType inventoryType, String title, String role) {
        Inventory inventory = new Inventory(business, inventoryType, title, role);
        return inventoryRepository.save(inventory);
    }

    @Transactional
    public Inventory updateInventory(Long id, Inventory newInventory) {
        return inventoryRepository.findById(id)
                .map(inventory -> {
                    inventory.setBusiness(newInventory.getBusiness());
                    inventory.setInventoryType(newInventory.getInventoryType());
                    inventory.setTitle(newInventory.getTitle());
                    inventory.setRole(newInventory.getRole());
                    return inventoryRepository.save(inventory);
                })
                .orElseThrow(() -> new InventoryNotFoundException(id));
    }

    @Transactional
    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }
}
