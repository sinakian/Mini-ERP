package dev.ordy.erp.sales.invoice;

import dev.ordy.erp.sales.order.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Transactional(readOnly = true)
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    @Transactional
    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Transactional
    public Invoice createInvoiceByOrder(Order order) {
        Invoice invoice= new Invoice(
                order.getBusiness(),
                order.getCustomer(),
                order,
                order.getTotalItemPrice(),
                order.getTotalLogisticPrice(),
                order.getDiscountInPercent(),
                order.getDiscountInCurrency(),
                order.getTotal(),
                order.getCurrency(),
                order.getPaymentType(),
                order.getCreatedBy(),
                Invoice.InvoiceStatus.INVOICE
        );
        return invoiceRepository.save(invoice);
    }

    @Transactional
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}
