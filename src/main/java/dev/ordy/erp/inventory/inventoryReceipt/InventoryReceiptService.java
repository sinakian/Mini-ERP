package dev.ordy.erp.inventory.inventoryReceipt;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryReceiptService {

    private final InventoryReceiptRepository inventoryReceiptRepository;

    public InventoryReceiptService(InventoryReceiptRepository inventoryReceiptRepository) {
        this.inventoryReceiptRepository = inventoryReceiptRepository;
    }

    @Transactional(readOnly = true)
    public List<InventoryReceipt> getAllInventoryReceipts() {
        return inventoryReceiptRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<InventoryReceipt> getInventoryReceiptById(Long id) {
        return inventoryReceiptRepository.findById(id);
    }

    @Transactional
    public InventoryReceipt createInventoryReceipt(InventoryReceipt inventoryReceipt) {
        return inventoryReceiptRepository.save(inventoryReceipt);
    }

    @Transactional
    public InventoryReceipt updateInventoryReceipt(Long id, InventoryReceipt newInventoryReceipt) {
        return inventoryReceiptRepository.findById(id)
                .map(inventoryReceipt -> {
                    inventoryReceipt.setBusiness(newInventoryReceipt.getBusiness());
                    inventoryReceipt.setInventory(newInventoryReceipt.getInventory());
                    inventoryReceipt.setQuantity(newInventoryReceipt.getQuantity());
                    inventoryReceipt.setDueDate(newInventoryReceipt.getDueDate());
                    inventoryReceipt.setDeliveredDate(newInventoryReceipt.getDeliveredDate());
                    inventoryReceipt.setReferenceType(newInventoryReceipt.getReferenceType());
                    inventoryReceipt.setReferenceId(newInventoryReceipt.getReferenceId());
                    inventoryReceipt.setStatus(newInventoryReceipt.getStatus());
                    return inventoryReceiptRepository.save(inventoryReceipt);
                })
                .orElseThrow(() -> new InventoryReceiptNotFoundException(id));
    }

    @Transactional
    public void deleteInventoryReceipt(Long id) {
        inventoryReceiptRepository.deleteById(id);
    }
}
