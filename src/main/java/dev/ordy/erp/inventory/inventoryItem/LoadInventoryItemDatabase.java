//package dev.ordy.erp.inventory.inventoryItem;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class LoadInventoryItemDatabase {
//
//    private static final Logger log = LoggerFactory.getLogger(LoadInventoryItemDatabase.class);
//
//
//    @Bean(name = "inventoryItemDatabaseInitializer")
//    CommandLineRunner initInventoryItemDatabase(InventoryItemRepository repository) {
//        return args -> {
//            log.info("Preloading " + repository.save(new InventoryItem("Acme Corporation", "item")));
//            log.info("Preloading " + repository.save(new InventoryItem("Wayne Enterprises", "item")));
//        };
//    }
//
//}
//
