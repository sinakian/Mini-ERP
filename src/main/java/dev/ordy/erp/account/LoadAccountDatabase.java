package dev.ordy.erp.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadAccountDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadAccountDatabase.class);

    @Bean(name = "accountDatabaseInitializer")
    CommandLineRunner initDatabase(AccountRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Account("Bilbo Baggins", "burglar")));
            log.info("Preloading " + repository.save(new Account("Frodo Baggins", "thief")));
        };
    }
}
