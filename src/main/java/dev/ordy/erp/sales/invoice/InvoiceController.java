package dev.ordy.erp.sales.invoice;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public List<Invoice> all() {
        return invoiceService.getAllInvoices();
    }

    @PostMapping
    public Invoice newInvoice(@RequestBody Invoice newInvoice) {
        return invoiceService.createInvoice(newInvoice);
    }

    @GetMapping("/{id}")
    public Invoice one(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id)
                .orElseThrow(() -> new InvoiceNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
    }
}
