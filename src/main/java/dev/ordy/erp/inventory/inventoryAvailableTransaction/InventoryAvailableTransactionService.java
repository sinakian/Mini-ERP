package dev.ordy.erp.inventory.inventoryAvailableTransaction;

import dev.ordy.erp.inventory.inventoryItem.InventoryItem;
import dev.ordy.erp.inventory.inventoryItem.InventoryItemService;
import dev.ordy.erp.inventory.inventoryRequestItem.InventoryRequestItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryAvailableTransactionService {

    private final InventoryAvailableTransactionRepository inventoryAvailableTransactionRepository;
    private final InventoryItemService inventoryItemService;

    public InventoryAvailableTransactionService(InventoryAvailableTransactionRepository inventoryAvailableTransactionRepository, InventoryItemService inventoryItemService) {
        this.inventoryAvailableTransactionRepository = inventoryAvailableTransactionRepository;
        this.inventoryItemService = inventoryItemService;
    }

    @Transactional(readOnly = true)
    public List<InventoryAvailableTransaction> getAllInventoryAvailableTransactions() {
        return inventoryAvailableTransactionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<InventoryAvailableTransaction> getInventoryAvailableTransactionById(Long id) {
        return inventoryAvailableTransactionRepository.findById(id);
    }

    @Transactional
    public InventoryAvailableTransaction createInventoryAvailableTransaction(InventoryRequestItem inventoryRequestItem) {

        double availableQuantity = inventoryItemService.getAvailableQuantity(inventoryRequestItem.getInventoryItem().getId());

        if (inventoryRequestItem.getStatus() != InventoryRequestItem.Status.PENDING) {
            throw new IllegalArgumentException("Only pending requests can be processed");
        }

        if (availableQuantity < inventoryRequestItem.getRequestQuantity()) {
            throw new IllegalArgumentException("Requested quantity is more than available quantity");
        }


        double oldBalance = availableQuantity;
        double newBalance = oldBalance - inventoryRequestItem.getRequestQuantity();

        InventoryAvailableTransaction inventoryAvailableTransaction = new InventoryAvailableTransaction(
                inventoryRequestItem.getInventoryRequest().getBusiness(),
                inventoryRequestItem.getInventoryRequest().getInventory(),
                inventoryRequestItem.getInventoryItem(),
                inventoryRequestItem.getRequestQuantity(),
                InventoryAvailableTransaction.TransactionType.OUT,
                inventoryRequestItem.getInventoryRequest(),
                inventoryRequestItem,
                oldBalance,
                newBalance
        );

        long inventoryItemId=inventoryRequestItem.getInventoryItem().getId();
        inventoryItemService.updateAvailableBalance(inventoryItemId,newBalance);


        // Save the InventoryAvailableTransaction
        return inventoryAvailableTransactionRepository.save(inventoryAvailableTransaction);
    }


    @Transactional
    public void deleteInventoryAvailableTransaction(Long id) {
        inventoryAvailableTransactionRepository.deleteById(id);
    }


}
