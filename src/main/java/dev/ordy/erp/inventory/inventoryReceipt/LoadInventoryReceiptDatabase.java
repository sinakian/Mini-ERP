package dev.ordy.erp.inventory.inventoryReceipt;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadInventoryReceiptDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadInventoryReceiptDatabase.class);

    @Bean(name = "inventoryReceiptDatabaseInitializer")
    CommandLineRunner initInventoryReceiptDatabase(InventoryReceiptRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new InventoryReceipt("Acme Corporation", "receipt")));
            log.info("Preloading " + repository.save(new InventoryReceipt("Wayne Enterprises", "receipt")));
        };
    }
}

