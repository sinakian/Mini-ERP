package dev.ordy.erp.business.business;

import dev.ordy.erp.business.account.Account;
import dev.ordy.erp.business.account.AccountRepository;
import dev.ordy.erp.business.account.AccountService;
import dev.ordy.erp.business.account.enums.AccountCategory;
import dev.ordy.erp.business.account.enums.AccountType;
import dev.ordy.erp.finance.accountBalance.AccountBalanceRepository;
import dev.ordy.erp.inventory.inventory.Inventory;
import dev.ordy.erp.inventory.inventory.InventoryRepository;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BusinessEventListener implements ApplicationListener<BusinessCreateEvent> {

    private final AccountService accountService;
    private final InventoryRepository inventoryRepository;


    public BusinessEventListener(AccountRepository accountRepository, AccountBalanceRepository accountBalanceRepository, AccountService accountService, InventoryRepository inventoryRepository) {
        this.accountService = accountService;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(BusinessCreateEvent event) {
        Business business = event.getBusiness();

        // Create Account
        Account account = accountService.createAccount("Default", "Business", "Default Business Account", "MYBUSINESS", business, AccountType.MYBUSINESS, AccountCategory.MYBUSINESS, null);

        // Create product Inventory
        Inventory inventory1 = new Inventory(business, Inventory.InventoryType.PRODUCT, "Product Inventory", "MYBUSINESS");
        inventoryRepository.save(inventory1);

        // Create Material Inventory
        Inventory inventory2 = new Inventory(business, Inventory.InventoryType.MATERIAL, "Material Inventory", "MYBUSINESS");
        inventoryRepository.save(inventory2);

        // Log a message
        System.out.println("Business Creation Event is completely done");
    }
}
