package dev.ordy.erp.inventory.inventoryTransaction;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadInventoryTransactionDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadInventoryTransactionDatabase.class);


    @Bean(name = "inventoryTransactionDatabaseInitializer")
    CommandLineRunner initInventoryTransactionDatabase(InventoryTransactionRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new InventoryTransaction("Acme Corporation", "Transaction")));
            log.info("Preloading " + repository.save(new InventoryTransaction("Wayne Enterprises", "transaction")));
        };
    }

}

