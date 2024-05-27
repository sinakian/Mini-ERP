//package dev.ordy.erp.finance.accountBalance;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class LoadAccountBalanceDatabase {
//
//    private static final Logger log = LoggerFactory.getLogger(LoadAccountBalanceDatabase.class);
//
//    @Bean(name = "AccountBalanceDatabaseInitializer")
//    CommandLineRunner initAccountBalanceDatabase(AccountBalanceRepository repository) {
//        return args -> {
//            log.info("Preloading " + repository.save(new AccountBalance("Acme Corporation", "Account-Balance")));
//            log.info("Preloading " + repository.save(new AccountBalance("Wayne Enterprises", "Account-Balance")));
//        };
//    }
//
//}
//
