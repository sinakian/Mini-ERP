package dev.ordy.erp.business.business;

import dev.ordy.erp.business.account.Account;
import dev.ordy.erp.business.account.AccountService;
import dev.ordy.erp.business.account.enums.AccountCategory;
import dev.ordy.erp.business.account.enums.AccountType;
import dev.ordy.erp.business.business_settings.BusinessSettings;
import dev.ordy.erp.business.step.StepService;
import dev.ordy.erp.business.step_set.StepSetService;
import dev.ordy.erp.business.step_set.StepSet;
import dev.ordy.erp.inventory.inventory.Inventory;
import dev.ordy.erp.inventory.inventory.InventoryRepository;
import dev.ordy.erp.business.business_settings.BusinessSettingsService;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import dev.ordy.erp.common.Currency;

@Component
public class BusinessEventListener implements ApplicationListener<BusinessCreateEvent> {

    private final AccountService accountService;
    private final InventoryRepository inventoryRepository;
    private final StepSetService stepSetService;
    private final StepService stepService;
    private final BusinessService businessService;
    private final BusinessSettingsService businessSettingsService;


    public BusinessEventListener(
                                 AccountService accountService,
                                 InventoryRepository inventoryRepository,
                                 StepSetService stepSetService,
                                 StepService stepService,
                                 BusinessService businessService,
                                 BusinessSettingsService businessSettingsService
    ) {
        this.accountService = accountService;
        this.inventoryRepository = inventoryRepository;
        this.stepSetService =stepSetService;
        this.stepService =stepService;
        this.businessService = businessService;
        this.businessSettingsService = businessSettingsService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(BusinessCreateEvent event) {

        Business business = event.getBusiness();
        Long businessId= business.getId();



        // Create Account
        Account account = new Account("Default",
                "BusinessSettings",
                "Default BusinessSettings Account",
                "MYBUSINESS",
                business,
                AccountType.MYBUSINESS,
                AccountCategory.MYBUSINESS,
                null);
        accountService.createAccount(account);

        // Create product Inventory
        Inventory productInventory = new Inventory(business,
                Inventory.InventoryType.PRODUCT,
                "Product Inventory",
                "MYBUSINESS");
        inventoryRepository.save(productInventory);


        // Create Material Inventory
        Inventory materialInventory = new Inventory(business, Inventory.InventoryType.MATERIAL, "Material Inventory", "MYBUSINESS");
        inventoryRepository.save(materialInventory);


        //Create Default Stepset
        StepSet defaultStepSet= stepSetService.createDefaultStepSet(business);


        //create Default Steps
        stepService.createStep("Created","Admin",business,defaultStepSet);
        stepService.createStep("Processing","Admin",business,defaultStepSet);
        stepService.createStep("Delivered","Admin",business,defaultStepSet);

        BusinessSettings businessSettings = new BusinessSettings(null,
                "Admin",
                Currency.EUR,
                materialInventory,
                productInventory,
                defaultStepSet
                );

        businessSettingsService.createBusinessSettings(businessId,businessSettings);

        // Log a message
        System.out.println("BusinessSettings Creation Event is completely done");
    }
}
