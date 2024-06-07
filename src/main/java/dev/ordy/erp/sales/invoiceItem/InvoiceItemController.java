package dev.ordy.erp.sales.invoiceItem;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice-items")
public class InvoiceItemController {

    private final InvoiceItemService invoiceItemService;

    public InvoiceItemController(InvoiceItemService invoiceItemService) {
        this.invoiceItemService = invoiceItemService;
    }

    @GetMapping
    public List<InvoiceItem> all() {
        return invoiceItemService.getAllInvoiceItems();
    }

    @PostMapping
    public InvoiceItem newInvoiceItem(@RequestBody InvoiceItem newInvoiceItem) {
        return invoiceItemService.createInvoiceItem(newInvoiceItem);
    }

    @GetMapping("/{id}")
    public InvoiceItem one(@PathVariable Long id) {
        return invoiceItemService.getInvoiceItemById(id)
                .orElseThrow(() -> new InvoiceItemNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public void deleteInvoiceItem(@PathVariable Long id) {
        invoiceItemService.deleteInvoiceItem(id);
    }
}
