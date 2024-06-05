package dev.ordy.erp.sales.invoiceItem;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice-items")
class InvoiceItemController {

    private final InvoiceItemRepository repository;

    InvoiceItemController(InvoiceItemRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    List<InvoiceItem> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping
    InvoiceItem newInvoiceItem(@RequestBody InvoiceItem newInvoiceItem) {
        return repository.save(newInvoiceItem);
    }

    // Single item

    @GetMapping("/{id}")
    InvoiceItem one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new InvoiceItemNotFoundException(id));
    }

//    @PutMapping("/{id}")
//    InvoiceItem replaceInvoiceItem(@RequestBody InvoiceItem newInvoiceItem, @PathVariable Long id) {
//
//        return repository.findById(id)
//                .map(orderItem -> {
//                    orderItem.setName(newInvoiceItem.getName());
//                    orderItem.setRole(newInvoiceItem.getRole());
//                    return repository.save(orderItem);
//                })
//                .orElseGet(() -> {
//                    newInvoiceItem.setId(id);
//                    return repository.save(newInvoiceItem);
//                });
//    }

    @DeleteMapping("/{id}")
    void deleteInvoiceItem(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
