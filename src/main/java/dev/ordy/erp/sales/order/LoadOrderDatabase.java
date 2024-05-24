package dev.ordy.erp.sales.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadOrderDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadOrderDatabase.class);

    @Bean(name = "OrderDatabaseInitializer")
    CommandLineRunner initOrderDatabase(OrderRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Order("Acme Corporation", "order")));
            log.info("Preloading " + repository.save(new Order("Wayne Enterprises", "order")));
        };
    }

}

