package dev.ordy.erp.finance.itemPrice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadItemPriceDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadItemPriceDatabase.class);

    @Bean(name = "ItemPriceDatabaseInitializer")
    CommandLineRunner initItemPriceDatabase(ItemPriceRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new ItemPrice("Acme Corporation", "Item-Price")));
            log.info("Preloading " + repository.save(new ItemPrice("Wayne Enterprises", "Item-Price")));
        };
    }

}

