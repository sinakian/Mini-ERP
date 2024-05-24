package dev.ordy.erp.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadInventoryDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadInventoryDatabase.class);

    @Bean(name = "inventoryDatabaseInitializer")
    CommandLineRunner initBusinessDatabase(InventoryRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Inventory("Acme Corporation", "Manufacturer")));
            log.info("Preloading " + repository.save(new Inventory("Wayne Enterprises", "Conglomerate")));
        };
    }
}

