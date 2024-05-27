package dev.ordy.erp.business.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import dev.ordy.erp.business.business.BusinessRepository;
import dev.ordy.erp.business.business.Business;
@Configuration
class LoadAccountDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadAccountDatabase.class);

    @Bean(name = "accountDatabaseInitializer")
    CommandLineRunner initDatabase(AccountRepository accountRepository, BusinessRepository businessRepository) {
        return args -> {
            // Create and save Business entities
            Business business1 = new Business("Shire Enterprises", "Company");
            Business business2 = new Business("Rivendell Services", "Consulting");
            businessRepository.save(business1);
            businessRepository.save(business2);

            // Preload Account entities with associated Business
            log.info("Preloading " + accountRepository.save(new Account("Bilbo", "Baggins", "Bilbo Baggins", "burglar", business1,AccountType.NATURAL,AccountCategory.CUSTOMER,Gender.MALE)));
            log.info("Preloading " + accountRepository.save(new Account("Bilbo", "Baggins", "Bilbo Baggins", "burglar", business1,AccountType.NATURAL,AccountCategory.CUSTOMER,Gender.FEMALE)));

            log.info("Preloading " + accountRepository.save(new Account("Bilbo", "Baggins", "Bilbo Baggins", "burglar", business2,AccountType.LEGAL,AccountCategory.SUPPLIER,Gender.LEGAL)));
            log.info("Preloading " + accountRepository.save(new Account("Bilbo", "Baggins", "Bilbo Baggins", "burglar", business2,AccountType.LEGAL,AccountCategory.SUPPLIER,Gender.LEGAL)));
        };
    }
}
