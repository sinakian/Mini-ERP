package dev.ordy.erp.supply.supplyRequestItem;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SupplyRequestItemService {

    private final SupplyRequestItemRepository supplyRequestItemRepository;

    public SupplyRequestItemService(SupplyRequestItemRepository supplyRequestItemRepository) {
        this.supplyRequestItemRepository = supplyRequestItemRepository;
    }

    @Transactional(readOnly = true)
    public List<SupplyRequestItem> getAllSupplyRequestItems() {
        return supplyRequestItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<SupplyRequestItem> getSupplyRequestItemById(Long id) {
        return supplyRequestItemRepository.findById(id);
    }

    @Transactional
    public SupplyRequestItem createSupplyRequestItem(SupplyRequestItem supplyRequestItem) {
        return supplyRequestItemRepository.save(supplyRequestItem);
    }

    @Transactional
    public void deleteSupplyRequestItem(Long id) {
        supplyRequestItemRepository.deleteById(id);
    }
}

