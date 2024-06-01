package dev.ordy.erp.business.business;

import dev.ordy.erp.business.account.*;
import dev.ordy.erp.business.item.*;
import dev.ordy.erp.finance.accountBalance.AccountBalance;
import dev.ordy.erp.finance.accountBalance.AccountBalanceRepository;
import dev.ordy.erp.finance.accountBalance.BalanceStatus;
import dev.ordy.erp.finance.accountBalance.Currency;
import dev.ordy.erp.finance.financialTransaction.FinancialTransaction;
import dev.ordy.erp.finance.financialTransaction.FinancialTransactionRepository;
import dev.ordy.erp.finance.financialTransaction.FinancialTransactionType;
import dev.ordy.erp.finance.financialTransaction.TransactionReferenceType;
import dev.ordy.erp.finance.itemPrice.ItemPrice;
import dev.ordy.erp.finance.itemPrice.ItemPriceRepository;
import dev.ordy.erp.inventory.inventory.Inventory;
import dev.ordy.erp.inventory.inventory.InventoryRepository;
import dev.ordy.erp.inventory.inventoryItem.InventoryItem;
import dev.ordy.erp.inventory.inventoryItem.InventoryItemRepository;
import dev.ordy.erp.inventory.inventoryReceipt.InventoryReceipt;
import dev.ordy.erp.inventory.inventoryReceipt.InventoryReceiptRepository;
import dev.ordy.erp.inventory.inventoryReceiptItem.InventoryReceiptItem;
import dev.ordy.erp.inventory.inventoryReceiptItem.InventoryReceiptItemRepository;
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
            BusinessRepository businessRepository,
            AccountRepository accountRepository,
            ItemRepository itemRepository,
            AccountBalanceRepository accountBalanceRepository,
            FinancialTransactionRepository financialTransactionRepository,
            ItemPriceRepository itemPriceRepository,
            InventoryRepository inventoryRepository,
            InventoryItemRepository inventoryItemRepository,
            InventoryReceiptRepository inventoryReceiptRepository,
            InventoryReceiptItemRepository inventoryReceiptItemRepository,
            InventoryTransactionRepository inventoryTransactionRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            SupplyRequestRepository supplyRequestRepository,
            SupplyRequestItemRepository supplyRequestItemRepository) {

        return args -> loadData(
                businessRepository,
                accountRepository,
                itemRepository,
                accountBalanceRepository,
                financialTransactionRepository,
                itemPriceRepository,
                inventoryRepository,
                inventoryItemRepository,
                inventoryReceiptRepository,
                inventoryReceiptItemRepository,
                inventoryTransactionRepository,
                orderRepository,
                orderItemRepository,
                supplyRequestRepository,
                supplyRequestItemRepository);
    }

    @Transactional
    void loadData(
            BusinessRepository businessRepository,
            AccountRepository accountRepository,
            ItemRepository itemRepository,
            AccountBalanceRepository accountBalanceRepository,
            FinancialTransactionRepository financialTransactionRepository,
            ItemPriceRepository itemPriceRepository,
            InventoryRepository inventoryRepository,
            InventoryItemRepository inventoryItemRepository,
            InventoryReceiptRepository inventoryReceiptRepository,
            InventoryReceiptItemRepository inventoryReceiptItemRepository,
            InventoryTransactionRepository inventoryTransactionRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            SupplyRequestRepository supplyRequestRepository,
            SupplyRequestItemRepository supplyRequestItemRepository) {

        // Business Entities
        Business business1 = new Business("Acme Corporation", "Manufacturer");
        Business business2 = new Business("Wayne Enterprises", "Conglomerate");
        businessRepository.save(business1);
        businessRepository.save(business2);
        log.info("Preloaded businesses");

        // Account Entities
        Account account1 = new Account("John", "Doe", "John Doe", "Customer", business1, AccountType.LEGAL, AccountCategory.CUSTOMER, Gender.MALE);
        Account account2 = new Account("Jane", "Smith", "Jane Smith", "Customer", business2, AccountType.NATURAL, AccountCategory.CUSTOMER, Gender.FEMALE);
        accountRepository.save(account1);
        accountRepository.save(account2);
        log.info("Preloaded accounts");


        // Item Entities
        Item item1 = new Item("Item A", "Role A", business1, InventoryPolicy.FLEXIBLE, ItemType.PRODUCT, Unit.KILOGRAM);
        Item item2 = new Item("Item B", "Role B", business2, InventoryPolicy.LIMITED, ItemType.SERVICE, Unit.KILOGRAM);
        itemRepository.save(item1);
        itemRepository.save(item2);
        log.info("Preloaded items");

        //finance

        //Account Balance
        AccountBalance accountBalance1 = new AccountBalance(account1, 1000.0, BalanceStatus.CREDIT, Currency.TOMAN);
        AccountBalance accountBalance2 = new AccountBalance(account2, 1500.0, BalanceStatus.DEBT, Currency.TOMAN);
        accountBalanceRepository.save(accountBalance1);
        accountBalanceRepository.save(accountBalance2);
        log.info("Preloaded account balances");

        //Financial Transaction
        FinancialTransaction transaction1 = new FinancialTransaction(
                FinancialTransactionType.CREDIT,
                TransactionReferenceType.CREDIT_RECEIPT,
                dev.ordy.erp.finance.financialTransaction.Currency.TOMAN,
                account1,
                500.0,
                1000.0,
                dev.ordy.erp.finance.financialTransaction.BalanceStatus.DEBT,
                1500.0,
                dev.ordy.erp.finance.financialTransaction.BalanceStatus.CREDIT,
                "REF12345"
        );

        FinancialTransaction transaction2 = new FinancialTransaction(
                FinancialTransactionType.DEBT,
                TransactionReferenceType.DEBT_RECEIPT,
                dev.ordy.erp.finance.financialTransaction.Currency.TOMAN,
                account2,
                300.0,
                1500.0,
                dev.ordy.erp.finance.financialTransaction.BalanceStatus.CREDIT,
                1200.0,
                dev.ordy.erp.finance.financialTransaction.BalanceStatus.CREDIT,
                "REF67890"
        );

        financialTransactionRepository.save(transaction1);
        financialTransactionRepository.save(transaction2);
        log.info("Preloaded financial transactions");

        //Item Price
        ItemPrice itemPrice1 = new ItemPrice(
                item1,
                100.0,
                ItemPrice.Unit.KILOGRAM,
                ItemPrice.Currency.TOMAN
        );

        ItemPrice itemPrice2 = new ItemPrice(
                item2,
                200.0,
                ItemPrice.Unit.KILOGRAM,
                ItemPrice.Currency.TOMAN
        );

        itemPriceRepository.save(itemPrice1);
        itemPriceRepository.save(itemPrice2);
        log.info("Preloaded item prices");

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
        log.info("Preloaded inventories");


        // InventoryItem Entities
        InventoryItem inventoryItem1 = new InventoryItem(
                inventory1,
                item1,
                100.0,
                80.0,
                InventoryItem.Unit.KILOGRAM,
                "Inventory Item 1",
                "Inventory Staff"
        );

        InventoryItem inventoryItem2 = new InventoryItem(
                inventory2,
                item2,
                200.0,
                150.0,
                InventoryItem.Unit.KILOGRAM,
                "Inventory Item 2",
                "Inventory Staff"
        );

        inventoryItemRepository.save(inventoryItem1);
        inventoryItemRepository.save(inventoryItem2);
        log.info("Preloaded inventory items");

        //Inventory Receipt
        InventoryReceipt inventoryReceipt1 = new InventoryReceipt(
                business1,
                inventory1,
                100.0,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                InventoryReceipt.ReferenceType.INVOICE,
                "INV001",
                InventoryReceipt.Status.PENDING
        );

        InventoryReceipt inventoryReceipt2 = new InventoryReceipt(
                business2,
                inventory2,
                200.0,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(5),
                InventoryReceipt.ReferenceType.DIRECT,
                "DIR002",
                InventoryReceipt.Status.DELIVERED
        );

        inventoryReceiptRepository.save(inventoryReceipt1);
        inventoryReceiptRepository.save(inventoryReceipt2);
        log.info("Preloaded inventory receipts");

        //Inventory Receipt Item
        InventoryReceiptItem inventoryReceiptItem1 = new InventoryReceiptItem(
                inventoryReceipt1,
                inventoryItem1,
                50.0,
                40.0,
                10.0,
                LocalDateTime.now().plusDays(7),
                LocalDateTime.now(),
                InventoryReceiptItem.Unit.PIECE,
                InventoryReceiptItem.Status.DELIVERED
        );

        InventoryReceiptItem inventoryReceiptItem2 = new InventoryReceiptItem(
                inventoryReceipt2,
                inventoryItem2,
                100.0,
                80.0,
                20.0,
                LocalDateTime.now().plusDays(5),
                null, // No delivered date yet
                InventoryReceiptItem.Unit.KILOGRAM,
                InventoryReceiptItem.Status.PENDING
        );

        inventoryReceiptItemRepository.save(inventoryReceiptItem1);
        inventoryReceiptItemRepository.save(inventoryReceiptItem2);
        log.info("Preloaded inventory receipt items");

        //inventory Transaction
        InventoryTransaction inventoryTransaction1 = new InventoryTransaction(
                business1,
                inventory1,
                inventoryItem1,
                20.0,
                InventoryTransaction.TransactionType.IN,
                inventoryReceipt1,
                inventoryReceiptItem1,
                100.0,
                120.0
        );

        InventoryTransaction inventoryTransaction2 = new InventoryTransaction(
                business2,
                inventory2,
                inventoryItem2,
                30.0,
                InventoryTransaction.TransactionType.OUT,
                inventoryReceipt2,
                inventoryReceiptItem2,
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
                Order.Currency.USD,
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
                Order.Currency.EUR,
                "PayPal",
                "Admin",
                Order.OrderStatus.INVOICE
        );

        orderRepository.save(order1);
        orderRepository.save(order2);
        log.info("Preloaded orders");


        // OrderItem Entities
        OrderItem orderItem1 = new OrderItem(
                business1,
                order1,
                inventoryItem1,
                5.0,
                itemPrice1,
                10.0,
                OrderItem.Unit.UNIT,
                OrderItem.Currency.USD,
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
                OrderItem.Unit.KG,
                OrderItem.Currency.EUR,
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
                SupplyRequestItem.Unit.PIECE,
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
                SupplyRequestItem.Unit.KILOGRAM,
                LocalDateTime.now(),
                SupplyRequestItem.Status.DELIVERED,
                "Admin"
        );

        supplyRequestItemRepository.save(supplyRequestItem1);
        supplyRequestItemRepository.save(supplyRequestItem2);
        log.info("Preloaded supply request items");


    }
}
