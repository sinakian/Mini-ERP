package dev.ordy.erp.inventory.inventoryTransaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryTransactionService {

    private final InventoryTransactionRepository inventoryTransactionRepository;

    public InventoryTransactionService(InventoryTransactionRepository inventoryTransactionRepository) {
        this.inventoryTransactionRepository = inventoryTransactionRepository;
    }

    @Transactional(readOnly = true)
    public List<InventoryTransaction> getAllInventoryTransactions() {
        return inventoryTransactionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<InventoryTransaction> getInventoryTransactionById(Long id) {
        return inventoryTransactionRepository.findById(id);
    }

    @Transactional
    public InventoryTransaction createInventoryTransaction(InventoryTransaction inventoryTransaction) {
        return inventoryTransactionRepository.save(inventoryTransaction);
    }

    @Transactional
    public void deleteInventoryTransaction(Long id) {
        inventoryTransactionRepository.deleteById(id);
    }
}
