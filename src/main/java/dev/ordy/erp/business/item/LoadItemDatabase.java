package dev.ordy.erp.business.item;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadItemDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadItemDatabase.class);


    @Bean(name = "ItemDatabaseInitializer")
    CommandLineRunner initItemDatabase(ItemRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Item("Acme Corporation", "item")));
            log.info("Preloading " + repository.save(new Item("Wayne Enterprises", "item")));
        };
    }

}

