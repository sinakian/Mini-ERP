package dev.ordy.erp.inventory.inventoryReceiptItem;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryReceiptItemService {

    private final InventoryReceiptItemRepository inventoryReceiptItemRepository;

    public InventoryReceiptItemService(InventoryReceiptItemRepository inventoryReceiptItemRepository) {
        this.inventoryReceiptItemRepository = inventoryReceiptItemRepository;
    }

    @Transactional(readOnly = true)
    public List<InventoryReceiptItem> getAllInventoryReceiptItems() {
        return inventoryReceiptItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<InventoryReceiptItem> getInventoryReceiptItemById(Long id) {
        return inventoryReceiptItemRepository.findById(id);
    }

    @Transactional
    public InventoryReceiptItem createInventoryReceiptItem(InventoryReceiptItem inventoryReceiptItem) {
        return inventoryReceiptItemRepository.save(inventoryReceiptItem);
    }

    @Transactional
    public InventoryReceiptItem updateInventoryReceiptItem(Long id, InventoryReceiptItem newInventoryReceiptItem) {
        return inventoryReceiptItemRepository.findById(id)
                .map(inventoryReceiptItem -> {
                    inventoryReceiptItem.setInventoryReceipt(newInventoryReceiptItem.getInventoryReceipt());
                    inventoryReceiptItem.setInventoryItem(newInventoryReceiptItem.getInventoryItem());
                    inventoryReceiptItem.setRequestQuantity(newInventoryReceiptItem.getRequestQuantity());
                    inventoryReceiptItem.setDeliveredQuantity(newInventoryReceiptItem.getDeliveredQuantity());
                    inventoryReceiptItem.setPendingQuantity(newInventoryReceiptItem.getPendingQuantity());
                    inventoryReceiptItem.setDueDate(newInventoryReceiptItem.getDueDate());
                    inventoryReceiptItem.setDeliveredDate(newInventoryReceiptItem.getDeliveredDate());
                    inventoryReceiptItem.setUnit(newInventoryReceiptItem.getUnit());
                    inventoryReceiptItem.setStatus(newInventoryReceiptItem.getStatus());
                    return inventoryReceiptItemRepository.save(inventoryReceiptItem);
                })
                .orElseThrow(() -> new InventoryReceiptItemNotFoundException(id));
    }

    @Transactional
    public void deleteInventoryReceiptItem(Long id) {
        inventoryReceiptItemRepository.deleteById(id);
    }
}
