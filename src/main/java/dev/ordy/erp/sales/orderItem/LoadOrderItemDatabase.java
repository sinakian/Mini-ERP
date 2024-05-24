package dev.ordy.erp.sales.orderItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadOrderItemDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadOrderItemDatabase.class);

    @Bean(name = "OrderItemDatabaseInitializer")
    CommandLineRunner initOrderItemDatabase(OrderItemRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new OrderItem("Acme Corporation", "order-item")));
            log.info("Preloading " + repository.save(new OrderItem("Wayne Enterprises", "order-item")));
        };
    }

}

