package dev.ordy.erp.inventory.inventoryTransaction;

import dev.ordy.erp.inventory.inventoryItem.InventoryItem;
import dev.ordy.erp.inventory.inventoryItem.InventoryItemService;
import dev.ordy.erp.inventory.inventoryRequestItem.InventoryRequestItem;
import dev.ordy.erp.inventory.inventoryRequestItem.InventoryRequestItemService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryTransactionService {

    private final InventoryTransactionRepository inventoryTransactionRepository;
    private final InventoryRequestItemService inventoryRequestItemService;
    private final InventoryItemService inventoryItemService;
    private final ApplicationEventPublisher eventPublisher;


    public InventoryTransactionService(InventoryTransactionRepository inventoryTransactionRepository, InventoryRequestItemService inventoryRequestItemService, @Lazy InventoryItemService inventoryItemService, ApplicationEventPublisher eventPublisher) {
        this.inventoryTransactionRepository = inventoryTransactionRepository;
        this.inventoryRequestItemService = inventoryRequestItemService;
        this.inventoryItemService = inventoryItemService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(readOnly = true)
    public List<InventoryTransaction> getAllInventoryTransactions() {
        return inventoryTransactionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public InventoryTransaction getInventoryTransactionById(Long id) {
        return inventoryTransactionRepository.findById(id)
                .orElseThrow(() -> new InventoryTransactionNotFoundException(id));
    }

    @Transactional
    public InventoryTransaction createInventoryTransaction(InventoryTransaction inventoryTransaction) {
        return inventoryTransactionRepository.save(inventoryTransaction);
    }

    @Transactional
    public InventoryTransaction inInventoryTransaction(long inventoryRequestItemId,long inventoryItemId,double quantity) {
        InventoryItem inventoryItem= inventoryItemService.getInventoryItemById(inventoryItemId);
        InventoryRequestItem inventoryRequestItem=inventoryRequestItemService.getInventoryRequestItemById(inventoryRequestItemId);
        double oldBalance= inventoryItem.getQuantity();
        double newBalance= oldBalance+quantity;
        InventoryTransaction inventoryTransaction = new InventoryTransaction(
                inventoryItem.getInventory().getBusiness(),
                inventoryItem.getInventory(),
                inventoryItem,
                quantity,
                InventoryTransaction.TransactionType.IN,
                inventoryRequestItem.getInventoryRequest(),
                inventoryRequestItem,
                oldBalance,
                newBalance
                );

        eventPublisher.publishEvent(new InventoryTransactionCreateEvent(this,inventoryTransaction));
        return inventoryTransactionRepository.save(inventoryTransaction);

    }

    @Transactional
    public InventoryTransaction outInventoryTransaction (long inventoryRequestItemId,long inventoryItemId,double quantity) {
        InventoryItem inventoryItem= inventoryItemService.getInventoryItemById(inventoryItemId);
        InventoryRequestItem inventoryRequestItem=inventoryRequestItemService.getInventoryRequestItemById(inventoryRequestItemId);
        double oldBalance= inventoryItem.getQuantity();
        if (oldBalance < quantity) {
            throw new IllegalArgumentException("Requested quantity exceeds available balance");
        }
        double newBalance= oldBalance-quantity;
        InventoryTransaction inventoryTransaction = new InventoryTransaction(
                inventoryItem.getInventory().getBusiness(),
                inventoryItem.getInventory(),
                inventoryItem,
                quantity,
                InventoryTransaction.TransactionType.OUT,
                inventoryRequestItem.getInventoryRequest(),
                inventoryRequestItem,
                oldBalance,
                newBalance
        );

        eventPublisher.publishEvent(new InventoryTransactionCreateEvent(this,inventoryTransaction));
        return inventoryTransactionRepository.save(inventoryTransaction);

    }

    @Transactional
    public void deleteInventoryTransaction(Long id) {
        inventoryTransactionRepository.deleteById(id);
    }
}
