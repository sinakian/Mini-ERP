package dev.ordy.erp.business.business;

import dev.ordy.erp.business.account.Account;
import dev.ordy.erp.business.account.AccountService;
import dev.ordy.erp.business.account.enums.AccountCategory;
import dev.ordy.erp.business.account.enums.AccountType;
import dev.ordy.erp.business.step.StepService;
import dev.ordy.erp.business.step_set.StepSetService;
import dev.ordy.erp.business.step_set.StepSet;
import dev.ordy.erp.inventory.inventory.Inventory;
import dev.ordy.erp.inventory.inventory.InventoryRepository;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BusinessEventListener implements ApplicationListener<BusinessCreateEvent> {

    private final AccountService accountService;
    private final InventoryRepository inventoryRepository;
    private final StepSetService stepSetService;
    private final StepService stepService;
    private final BusinessService businessService;


    public BusinessEventListener(
                                 AccountService accountService,
                                 InventoryRepository inventoryRepository,
                                 StepSetService stepSetService,
                                 StepService stepService,
                                 BusinessService businessService
    ) {
        this.accountService = accountService;
        this.inventoryRepository = inventoryRepository;
        this.stepSetService =stepSetService;
        this.stepService =stepService;
        this.businessService = businessService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(BusinessCreateEvent event) {
        Business business = event.getBusiness();

        // Create Account
        Account account = new Account("Default", "Business", "Default Business Account", "MYBUSINESS", business, AccountType.MYBUSINESS, AccountCategory.MYBUSINESS, null);
        accountService.createAccount(account);

        // Create product Inventory
        Inventory productInventory = new Inventory(business, Inventory.InventoryType.PRODUCT, "Product Inventory", "MYBUSINESS");
        inventoryRepository.save(productInventory);
        business.setDefaultProductInventory(productInventory);

        // Create Material Inventory
        Inventory materialInventory = new Inventory(business, Inventory.InventoryType.MATERIAL, "Material Inventory", "MYBUSINESS");
        inventoryRepository.save(materialInventory);
        business.setDefaultMaterialInventory(materialInventory);

        //Create Default Stepset
        StepSet defaultStepSet= stepSetService.createDefaultStepSet(business);
        businessService.setDefaultStepset(business,defaultStepSet);


        //create Default Steps
        stepService.createStep("Created","Admin",business,defaultStepSet);
        stepService.createStep("Processing","Admin",business,defaultStepSet);
        stepService.createStep("Delivered","Admin",business,defaultStepSet);

        // Log a message
        System.out.println("Business Creation Event is completely done");
    }
}
