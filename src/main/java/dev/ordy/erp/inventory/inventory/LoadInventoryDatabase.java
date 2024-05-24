package dev.ordy.erp.inventory.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadInventoryDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadInventoryDatabase.class);

    @Bean(name = "inventoryDatabaseInitializer")
    CommandLineRunner initInventoryDatabase(InventoryRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Inventory("Acme Corporation", "Manufacturer")));
            log.info("Preloading " + repository.save(new Inventory("Wayne Enterprises", "Conglomerate")));
        };
    }
//    @Bean(name = "inventoryItemDatabaseInitializer")
//    CommandLineRunner initInventoryItemDatabase(InventoryItemRepository repository) {
//        return args -> {
//            log.info("Preloading " + repository.save(new InventoryItem("Acme Corporation", "Manufacturer")));
//            log.info("Preloading " + repository.save(new InventoryItem("Wayne Enterprises", "Conglomerate")));
//        };
//    }
//    @Bean(name = "inventoryTransactionDatabaseInitializer")
//    CommandLineRunner initInventoryTransactionDatabase(InventoryTransactionRepository repository) {
//        return args -> {
//            log.info("Preloading " + repository.save(new InventoryTransaction("Acme Corporation", "Manufacturer")));
//            log.info("Preloading " + repository.save(new InventoryTransaction("Wayne Enterprises", "Conglomerate")));
//        };
//    }
//    @Bean(name = "inventoryReceiptDatabaseInitializer")
//    CommandLineRunner initInventoryReceiptDatabase(InventoryReceiptRepository repository) {
//        return args -> {
//            log.info("Preloading " + repository.save(new InventoryReceipt("Acme Corporation", "Manufacturer")));
//            log.info("Preloading " + repository.save(new InventoryReceipt("Wayne Enterprises", "Conglomerate")));
//        };
//    }
}

