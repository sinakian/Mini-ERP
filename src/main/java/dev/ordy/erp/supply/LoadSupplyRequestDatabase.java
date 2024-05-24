package dev.ordy.erp.supply;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadSupplyRequestDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadSupplyRequestDatabase.class);

    @Bean(name = "supplyRequestDatabaseInitializer")
    CommandLineRunner initSupplyRequestDatabase(SupplyRequestRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new SupplyRequest("Acme Corporation", "supply-request")));
            log.info("Preloading " + repository.save(new SupplyRequest("Wayne Enterprises", "supply-request")));
        };
    }

}

