package dev.ordy.erp.business.business;

import dev.ordy.erp.business.account.Account;
import dev.ordy.erp.business.account.AccountRepository;
import dev.ordy.erp.business.account.enums.AccountCategory;
import dev.ordy.erp.business.account.enums.AccountType;
import dev.ordy.erp.finance.accountBalance.AccountBalance;
import dev.ordy.erp.finance.accountBalance.AccountBalanceRepository;
import dev.ordy.erp.finance.accountBalance.BalanceStatus;
import dev.ordy.erp.inventory.inventory.Inventory;
import dev.ordy.erp.inventory.inventory.InventoryRepository;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BusinessEventListener implements ApplicationListener<BusinessEvent> {

    private final AccountRepository accountRepository;
    private final AccountBalanceRepository accountBalanceRepository;
    private final InventoryRepository inventoryRepository;


    public BusinessEventListener(AccountRepository accountRepository, AccountBalanceRepository accountBalanceRepository, InventoryRepository inventoryRepository) {
        this.accountRepository = accountRepository;
        this.accountBalanceRepository = accountBalanceRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(BusinessEvent event) {
        Business business = event.getBusiness();

        // Create Account
        Account account = new Account("Default", "Business", "Default Business Account", "MYBUSINESS", business, AccountType.MYBUSINESS, AccountCategory.MYBUSINESS, null);
        account = accountRepository.save(account);

        // Create AccountBalance
        AccountBalance accountBalance = new AccountBalance(account, 0.0, BalanceStatus.NEUTRAL, null);
        accountBalanceRepository.save(accountBalance);

        // Create product Inventory
        Inventory inventory1 = new Inventory(business, Inventory.InventoryType.PRODUCT, "Product Inventory", "MYBUSINESS");
        inventoryRepository.save(inventory1);

        // Create Material Inventory
        Inventory inventory2 = new Inventory(business, Inventory.InventoryType.MATERIAL, "Material Inventory", "MYBUSINESS");
        inventoryRepository.save(inventory2);

        // Log a message
        System.out.println("Event is completely done");
    }
}
