//package dev.ordy.erp.inventory.inventoryReceiptItem;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class LoadInventoryReceiptItemDatabase {
//
//    private static final Logger log = LoggerFactory.getLogger(LoadInventoryReceiptItemDatabase.class);
//
//    @Bean(name = "inventoryReceiptDatabaseInitializer")
//    CommandLineRunner initInventoryReceiptDatabase(InventoryReceiptItemRepository repository) {
//        return args -> {
//            log.info("Preloading " + repository.save(new InventoryReceiptItem("Acme Corporation", "receipt")));
//            log.info("Preloading " + repository.save(new InventoryReceiptItem("Wayne Enterprises", "receipt")));
//        };
//    }
//}
//
