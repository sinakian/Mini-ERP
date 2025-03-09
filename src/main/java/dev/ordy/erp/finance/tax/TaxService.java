package dev.ordy.erp.finance.tax;

import dev.ordy.erp.common.Currency;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaxService {

    private final TaxRepository taxRepository;

    public TaxService(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

    @Transactional(readOnly = true)
    public List<Tax> getAllTaxes() {
        return taxRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Tax> getTaxesByBusinessId(String businessId) {
        return taxRepository.findByBusinessId(businessId);
    }

    @Transactional(readOnly = true)
    public Optional<Tax> getTaxById(Long id) {
        return taxRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Tax> getTaxByCode(String code, String businessId) {
        return taxRepository.findByCodeAndBusinessId(code, businessId);
    }

    @Transactional
    public Tax createTax(String name, String code, Double rate, String description, Boolean active, Currency currency, String businessId) {
        Tax tax = new Tax(name, code, rate, description, active, currency, businessId);
        return taxRepository.save(tax);
    }

    @Transactional
    public Tax updateTax(Long id, Tax newTax) {
        return taxRepository.findById(id)
                .map(tax -> {
                    tax.setName(newTax.getName());
                    tax.setCode(newTax.getCode());
                    tax.setRate(newTax.getRate());
                    tax.setDescription(newTax.getDescription());
                    tax.setActive(newTax.getActive());
                    tax.setCurrency(newTax.getCurrency());
                    // We don't update businessId as it's a tenant identifier
                    return taxRepository.save(tax);
                })
                .orElseThrow(() -> new TaxNotFoundException(id));
    }

    @Transactional
    public void deleteTax(Long id) {
        taxRepository.deleteById(id);
    }
}