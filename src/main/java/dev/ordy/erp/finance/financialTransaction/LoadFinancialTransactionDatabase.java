//package dev.ordy.erp.finance.financialTransaction;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class LoadFinancialTransactionDatabase {
//
//    private static final Logger log = LoggerFactory.getLogger(LoadFinancialTransactionDatabase.class);
//
//    @Bean(name = "FinancialTransactionDatabaseInitializer")
//    CommandLineRunner initFinancialTransactionDatabase(FinancialTransactionRepository repository) {
//        return args -> {
//            log.info("Preloading " + repository.save(new FinancialTransaction("Acme Corporation", "Financial-transaction")));
//            log.info("Preloading " + repository.save(new FinancialTransaction("Wayne Enterprises", "Financial-transaction")));
//        };
//    }
//
//}
//
