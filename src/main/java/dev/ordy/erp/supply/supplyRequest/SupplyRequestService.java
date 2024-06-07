package dev.ordy.erp.supply.supplyRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SupplyRequestService {

    private final SupplyRequestRepository supplyRequestRepository;

    public SupplyRequestService(SupplyRequestRepository supplyRequestRepository) {
        this.supplyRequestRepository = supplyRequestRepository;
    }

    @Transactional(readOnly = true)
    public List<SupplyRequest> getAllSupplyRequests() {
        return supplyRequestRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<SupplyRequest> getSupplyRequestById(Long id) {
        return supplyRequestRepository.findById(id);
    }

    @Transactional
    public SupplyRequest createSupplyRequest(SupplyRequest supplyRequest) {
        return supplyRequestRepository.save(supplyRequest);
    }

    @Transactional
    public void deleteSupplyRequest(Long id) {
        supplyRequestRepository.deleteById(id);
    }
}
