package dev.ordy.erp.business.business;

import dev.ordy.erp.business.account.*;
import dev.ordy.erp.business.account.enums.AccountCategory;
import dev.ordy.erp.business.account.enums.AccountType;
import dev.ordy.erp.business.item.*;
import dev.ordy.erp.business.item.enums.InventoryPolicy;
import dev.ordy.erp.business.item.enums.ItemType;
import dev.ordy.erp.common.*;
import dev.ordy.erp.finance.accountBalance.AccountBalance;
import dev.ordy.erp.finance.accountBalance.AccountBalanceRepository;
import dev.ordy.erp.common.Currency;
import dev.ordy.erp.finance.accountBalance.BalanceStatus;
import dev.ordy.erp.finance.financialTransaction.FinancialTransaction;
import dev.ordy.erp.finance.financialTransaction.FinancialTransactionRepository;
import dev.ordy.erp.finance.financialTransaction.enums.FinancialTransactionType;
import dev.ordy.erp.finance.financialTransaction.enums.TransactionReferenceType;
import dev.ordy.erp.finance.itemPrice.ItemPrice;
import dev.ordy.erp.finance.itemPrice.ItemPriceRepository;
import dev.ordy.erp.finance.itemPrice.ItemPriceService;
import dev.ordy.erp.inventory.inventory.Inventory;
import dev.ordy.erp.inventory.inventory.InventoryRepository;
import dev.ordy.erp.inventory.inventoryItem.InventoryItem;
import dev.ordy.erp.inventory.inventoryItem.InventoryItemRepository;
import dev.ordy.erp.inventory.inventoryRequest.InventoryRequest;
import dev.ordy.erp.inventory.inventoryRequest.InventoryRequestRepository;
import dev.ordy.erp.inventory.inventoryRequestItem.InventoryRequestItem;
import dev.ordy.erp.inventory.inventoryRequestItem.InventoryRequestItemRepository;
import dev.ordy.erp.inventory.inventoryTransaction.InventoryTransaction;
import dev.ordy.erp.inventory.inventoryTransaction.InventoryTransactionRepository;
import dev.ordy.erp.sales.order.Order;
import dev.ordy.erp.sales.order.OrderRepository;
import dev.ordy.erp.sales.orderItem.OrderItem;
import dev.ordy.erp.sales.orderItem.OrderItemRepository;
import dev.ordy.erp.supply.supplyRequest.SupplyRequest;
import dev.ordy.erp.supply.supplyRequest.SupplyRequestRepository;
import dev.ordy.erp.supply.supplyRequestItem.SupplyRequestItem;
import dev.ordy.erp.supply.supplyRequestItem.SupplyRequestItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;

@Configuration
public class LoadBusinessDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadBusinessDatabase.class);

    @Bean
    CommandLineRunner initDatabase(
            BusinessService businessService,
            AccountService accountService,
            ItemService itemService,
            ItemPriceService itemPriceService,
            FinancialTransactionRepository financialTransactionRepository,
            InventoryRepository inventoryRepository,
            InventoryItemRepository inventoryItemRepository,
            InventoryRequestRepository inventoryRequestRepository,
            InventoryRequestItemRepository inventoryRequestItemRepository,
            InventoryTransactionRepository inventoryTransactionRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            SupplyRequestRepository supplyRequestRepository,
            SupplyRequestItemRepository supplyRequestItemRepository) {

        return args -> loadData(
                accountService,
                itemService,
                itemPriceService,
                financialTransactionRepository,
                inventoryRepository,
                inventoryItemRepository,
                inventoryRequestRepository,
                inventoryRequestItemRepository,
                inventoryTransactionRepository,
                orderRepository,
                orderItemRepository,
                supplyRequestRepository,
                supplyRequestItemRepository,
                businessService);
    }

    @Transactional
    void loadData(
            AccountService accountService,
            ItemService itemService,
            ItemPriceService itemPriceService,
            FinancialTransactionRepository financialTransactionRepository,
            InventoryRepository inventoryRepository,
            InventoryItemRepository inventoryItemRepository,
            InventoryRequestRepository inventoryRequestRepository,
            InventoryRequestItemRepository inventoryRequestItemRepository,
            InventoryTransactionRepository inventoryTransactionRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            SupplyRequestRepository supplyRequestRepository,
            SupplyRequestItemRepository supplyRequestItemRepository, BusinessService businessService) {

        // Business Entities
        Business business1 = new Business("Acme Corporation", "Manufacturer",Currency.TOMAN,null,null);
        Business business2 = new Business("Wayne Enterprises", "Conglomerate",Currency.TOMAN,null,null);
        businessService.createBusiness(business1);
        businessService.createBusiness(business2);
        log.info("Preloaded businesses");

        // Account Entities
        Account account1 = new Account("John", "Doe", "John Doe", "Customer", business1, AccountType.LEGAL, AccountCategory.CUSTOMER, Gender.MALE);
        Account account2 = new Account("Jane", "Smith", "Jane Smith", "Customer", business2, AccountType.NATURAL, AccountCategory.CUSTOMER, Gender.FEMALE);
        accountService.createAccount(account1);
        accountService.createAccount(account2);
        log.info("Preloaded accounts");


        // Item Entities
        Item item1 = itemService.createItem("Item A", "Role A", business1, InventoryPolicy.FLEXIBLE, ItemType.PRODUCT, Unit.KILOGRAM);
        Item item2 = itemService.createItem("Item B", "Role B", business2, InventoryPolicy.LIMITED, ItemType.SERVICE, Unit.KILOGRAM);
        log.info("Preloaded items");



        //Financial Transaction
//        FinancialTransaction transaction1 = new FinancialTransaction(
//                FinancialTransactionType.CREDIT,
//                TransactionReferenceType.CREDIT_RECEIPT,
//                Currency.TOMAN,
//                account1,
//                500.0,
//                1000.0,
//                BalanceStatus.DEBT,
//                1500.0,
//                BalanceStatus.CREDIT,
//                "REF12345"
//        );
//
//        FinancialTransaction transaction2 = new FinancialTransaction(
//                FinancialTransactionType.DEBT,
//                TransactionReferenceType.DEBT_RECEIPT,
//                Currency.TOMAN,
//                account2,
//                300.0,
//                1500.0,
//                BalanceStatus.CREDIT,
//                1200.0,
//                BalanceStatus.CREDIT,
//                "REF67890"
//        );
//
//        financialTransactionRepository.save(transaction1);
//        financialTransactionRepository.save(transaction2);
//        log.info("Preloaded financial transactions");


        //Inventory
        Inventory inventory1 = new Inventory(
                business1,
                Inventory.InventoryType.PRODUCT,
                "Main Warehouse",
                "Storage"
        );


        Inventory inventory2 = new Inventory(
                business2,
                Inventory.InventoryType.PRODUCT,
                "Retail Store",
                "Sales"
        );


        inventoryRepository.save(inventory1);
        inventoryRepository.save(inventory2);
        business1.setDefaultProductInventory(inventory1);
        business2.setDefaultProductInventory(inventory2);
        businessService.updateBusiness(business1.getId(), business1);
        businessService.updateBusiness(business2.getId(), business2);

        log.info("Preloaded inventories");


        // InventoryItem Entities
        InventoryItem inventoryItem1 = new InventoryItem(
                inventory1,
                item1,
                100.0,
                80.0,
                Unit.KILOGRAM,
                "Inventory Item 1",
                "Inventory Staff"
        );

        InventoryItem inventoryItem2 = new InventoryItem(
                inventory2,
                item2,
                200.0,
                150.0,
                Unit.KILOGRAM,
                "Inventory Item 2",
                "Inventory Staff"
        );

        inventoryItemRepository.save(inventoryItem1);
        inventoryItemRepository.save(inventoryItem2);
        log.info("Preloaded inventory items");

        //Inventory Receipt
        InventoryRequest inventoryRequest1 = new InventoryRequest(
                business1,
                inventory1,
                100.0,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                InventoryRequest.ReferenceType.INVOICE,
                1,
                InventoryRequest.Status.PENDING
        );

        InventoryRequest inventoryRequest2 = new InventoryRequest(
                business2,
                inventory2,
                200.0,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(5),
                InventoryRequest.ReferenceType.DIRECT,
                2,
                InventoryRequest.Status.DELIVERED
        );

        inventoryRequestRepository.save(inventoryRequest1);
        inventoryRequestRepository.save(inventoryRequest2);
        log.info("Preloaded inventory receipts");

        //Inventory Receipt Item
        InventoryRequestItem inventoryRequestItem1 = new InventoryRequestItem(
                inventoryRequest1,
                inventoryItem1,
                50.0,
                40.0,
                10.0,
                LocalDateTime.now().plusDays(7),
                LocalDateTime.now(),
                Unit.PIECE,
                InventoryRequestItem.Status.DELIVERED
        );

        InventoryRequestItem inventoryRequestItem2 = new InventoryRequestItem(
                inventoryRequest2,
                inventoryItem2,
                100.0,
                80.0,
                20.0,
                LocalDateTime.now().plusDays(5),
                null, // No delivered date yet
                Unit.KILOGRAM,
                InventoryRequestItem.Status.PENDING
        );

        inventoryRequestItemRepository.save(inventoryRequestItem1);
        inventoryRequestItemRepository.save(inventoryRequestItem2);
        log.info("Preloaded inventory receipt items");

        //inventory Transaction
        InventoryTransaction inventoryTransaction1 = new InventoryTransaction(
                business1,
                inventory1,
                inventoryItem1,
                20.0,
                InventoryTransaction.TransactionType.IN,
                inventoryRequest1,
                inventoryRequestItem1,
                100.0,
                120.0
        );

        InventoryTransaction inventoryTransaction2 = new InventoryTransaction(
                business2,
                inventory2,
                inventoryItem2,
                30.0,
                InventoryTransaction.TransactionType.OUT,
                inventoryRequest2,
                inventoryRequestItem2,
                200.0,
                170.0
        );

        inventoryTransactionRepository.save(inventoryTransaction1);
        inventoryTransactionRepository.save(inventoryTransaction2);
        log.info("Preloaded inventory transactions");

        // Order Entities
        Order order1 = new Order(
                business1,
                account1,
                500.0,
                50.0,
                10.0,
                20.0,
                530.0,
                Currency.USD,
                "Credit Card",
                "Admin",
                Order.OrderStatus.PENDING
        );

        Order order2 = new Order(
                business2,
                account2,
                700.0,
                70.0,
                15.0,
                30.0,
                740.0,
                Currency.EUR,
                "PayPal",
                "Admin",
                Order.OrderStatus.INVOICE
        );

        orderRepository.save(order1);
        orderRepository.save(order2);
        log.info("Preloaded orders");


        // OrderItem Entities
        ItemPrice itemPrice1 = itemPriceService.getItemPriceByItem(item1);
        ItemPrice itemPrice2 = itemPriceService.getItemPriceByItem(item2);


        OrderItem orderItem1 = new OrderItem(
                business1,
                order1,
                inventoryItem1,
                5.0,
                itemPrice1,
                10.0,
                Unit.UNIT,
                Currency.USD,
                100.0,
                20.0,
                5.0,
                8.0,
                90.0,
                "Admin"
        );

        OrderItem orderItem2 = new OrderItem(
                business2,
                order2,
                inventoryItem2,
                3.0,
                itemPrice2,
                15.0,
                Unit.KILOGRAM,
                Currency.EUR,
                150.0,
                25.0,
                7.0,
                10.0,
                135.0,
                "Admin"
        );

        orderItemRepository.save(orderItem1);
        orderItemRepository.save(orderItem2);
        log.info("Preloaded order items");


        // SupplyRequest Entities
        SupplyRequest supplyRequest1 = new SupplyRequest(
                business1,
                100.0,
                50.0,
                50.0,
                0.0,
                LocalDateTime.now(),
                SupplyRequest.ReferenceType.INVOICE,
                "INV123",
                SupplyRequest.Status.PENDING,
                "Admin"
        );

        SupplyRequest supplyRequest2 = new SupplyRequest(
                business2,
                200.0,
                150.0,
                50.0,
                0.0,
                LocalDateTime.now(),
                SupplyRequest.ReferenceType.DIRECT,
                "DIR456",
                SupplyRequest.Status.SUPPLIED,
                "Admin"
        );

        supplyRequestRepository.save(supplyRequest1);
        supplyRequestRepository.save(supplyRequest2);
        log.info("Preloaded supply requests");


        // SupplyRequestItem Entities
        SupplyRequestItem supplyRequestItem1 = new SupplyRequestItem(
                supplyRequest1,
                business1,
                item1,
                50.0,
                25.0,
                25.0,
                0.0,
                Unit.PIECE,
                LocalDateTime.now(),
                SupplyRequestItem.Status.PENDING,
                "Admin"
        );

        SupplyRequestItem supplyRequestItem2 = new SupplyRequestItem(
                supplyRequest2,
                business2,
                item2,
                100.0,
                75.0,
                25.0,
                0.0,
                Unit.KILOGRAM,
                LocalDateTime.now(),
                SupplyRequestItem.Status.DELIVERED,
                "Admin"
        );

        supplyRequestItemRepository.save(supplyRequestItem1);
        supplyRequestItemRepository.save(supplyRequestItem2);
        log.info("Preloaded supply request items");


    }
}
