package dev.ordy.erp.sales.invoiceItem;

import dev.ordy.erp.sales.invoice.Invoice;
import dev.ordy.erp.sales.order.Order;
import dev.ordy.erp.sales.orderItem.OrderItem;
import dev.ordy.erp.sales.orderItem.OrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceItemService {

    private final InvoiceItemRepository invoiceItemRepository;
    private final OrderItemService orderItemService;


    public InvoiceItemService(InvoiceItemRepository invoiceItemRepository, OrderItemService orderItemService) {
        this.invoiceItemRepository = invoiceItemRepository;
        this.orderItemService = orderItemService;
    }

    @Transactional(readOnly = true)
    public List<InvoiceItem> getAllInvoiceItems() {
        return invoiceItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<InvoiceItem> getInvoiceItemById(Long id) {
        return invoiceItemRepository.findById(id);
    }

    @Transactional
    public InvoiceItem createInvoiceItem(InvoiceItem invoiceItem) {
        return invoiceItemRepository.save(invoiceItem);
    }

    @Transactional
    public List<InvoiceItem> createInvoiceItemsByOrder(Order order, Invoice invoice) {
        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrder(order);

        List<InvoiceItem> invoiceItems = orderItems.stream().map(orderItem ->
                new InvoiceItem(
                        orderItem.getBusiness(),
                        invoice,
                        orderItem.getItem(),
                        orderItem.getQuantity(),
                        orderItem.getItemPrice(),
                        orderItem.getPricePerUnit(),
                        orderItem.getUnit(),
                        orderItem.getCurrency(),
                        orderItem.getTotalGrossPrice(),
                        orderItem.getDiscountCurrency(),
                        orderItem.getDiscountPercent(),
                        orderItem.getTax(),
                        orderItem.getTotalNetPrice(),
                        orderItem.getCreatedBy()
                )
        ).collect(Collectors.toList());

        return invoiceItemRepository.saveAll(invoiceItems);
    }

    @Transactional
    public void deleteInvoiceItem(Long id) {
        invoiceItemRepository.deleteById(id);
    }
}
