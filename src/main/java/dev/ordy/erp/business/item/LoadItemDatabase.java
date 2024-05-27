package dev.ordy.erp.business.item;


import dev.ordy.erp.business.business.Business;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import dev.ordy.erp.business.business.BusinessRepository;

@Configuration
public class LoadItemDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadItemDatabase.class);


    @Bean(name = "ItemDatabaseInitializer")
    CommandLineRunner initItemDatabase(ItemRepository repository, BusinessRepository businessRepository) {
        return args -> {
            Business business1 = new Business("sina", "Company");
            Business business2 = new Business("reza Services", "Consulting");
            businessRepository.save(business1);
            businessRepository.save(business2);

            log.info("Preloading " + repository.save(new Item("Acme Corporation", "item",business1,InventoryPolicy.UNLIMITED,ItemType.PRODUCT,Unit.KILOGRAM)));
            log.info("Preloading " + repository.save(new Item("Wayne Enterprises", "item",business2,InventoryPolicy.FLEXIBLE,ItemType.PRODUCT,Unit.GRAM)));
        };
    }

}

