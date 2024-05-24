package dev.ordy.erp.business.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadBusinessDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadBusinessDatabase.class);

    @Bean(name = "businessDatabaseInitializer")
    CommandLineRunner initBusinessDatabase(BusinessRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Business("Acme Corporation", "Manufacturer")));
            log.info("Preloading " + repository.save(new Business("Wayne Enterprises", "Conglomerate")));
        };
    }
}

