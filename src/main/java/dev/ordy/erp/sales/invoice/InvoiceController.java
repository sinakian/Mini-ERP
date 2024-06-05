package dev.ordy.erp.sales.invoice;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
class InvoiceController {

    private final InvoiceRepository repository;

    InvoiceController(InvoiceRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    List<Invoice> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping
    Invoice newInvoice(@RequestBody Invoice newInvoice) {
        return repository.save(newInvoice);
    }

    // Single item

    @GetMapping("/{id}")
    Invoice one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new InvoiceNotFoundException(id));
    }

//    @PutMapping("/{id}")
//    Invoice replaceInvoice(@RequestBody Invoice newInvoice, @PathVariable Long id) {
//
//        return repository.findById(id)
//                .map(order -> {
//                    order.setName(newInvoice.getName());
//                    order.setRole(newInvoice.getRole());
//                    return repository.save(order);
//                })
//                .orElseGet(() -> {
//                    newInvoice.setId(id);
//                    return repository.save(newInvoice);
//                });
//    }

    @DeleteMapping("/{id}")
    void deleteInvoice(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
