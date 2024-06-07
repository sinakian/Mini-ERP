package dev.ordy.erp.supply.supplyRequestItem;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supply-request-items")
public class SupplyRequestItemController {

    private final SupplyRequestItemService supplyRequestItemService;

    public SupplyRequestItemController(SupplyRequestItemService supplyRequestItemService) {
        this.supplyRequestItemService = supplyRequestItemService;
    }

    @GetMapping
    public List<SupplyRequestItem> all() {
        return supplyRequestItemService.getAllSupplyRequestItems();
    }

    @PostMapping
    public SupplyRequestItem newSupplyRequestItem(@RequestBody SupplyRequestItem newSupplyRequestItem) {
        return supplyRequestItemService.createSupplyRequestItem(newSupplyRequestItem);
    }

    @GetMapping("/{id}")
    public SupplyRequestItem one(@PathVariable Long id) {
        return supplyRequestItemService.getSupplyRequestItemById(id)
                .orElseThrow(() -> new SupplyRequestItemNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public void deleteSupplyRequestItem(@PathVariable Long id) {
        supplyRequestItemService.deleteSupplyRequestItem(id);
    }
}
