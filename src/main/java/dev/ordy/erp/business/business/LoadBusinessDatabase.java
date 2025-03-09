package dev.ordy.erp.business.business;

import dev.ordy.erp.business.account.*;
import dev.ordy.erp.business.account.enums.AccountCategory;
import dev.ordy.erp.business.account.enums.AccountType;
import dev.ordy.erp.business.business_settings.BusinessSettingsService;
import dev.ordy.erp.business.item.*;
import dev.ordy.erp.business.item.enums.InventoryPolicy;
import dev.ordy.erp.business.item.enums.ItemType;
import dev.ordy.erp.business.step_set.StepSetService;
import dev.ordy.erp.common.*;
import dev.ordy.erp.common.Currency;
import dev.ordy.erp.finance.financialReceipt.FinancialReceiptReferenceType;
import dev.ordy.erp.finance.financialReceipt.FinancialReceiptService;
import dev.ordy.erp.finance.accountBalance.AccountBalanceService;
import dev.ordy.erp.finance.financialReceipt.TransactionStatus;
import dev.ordy.erp.finance.financialTransaction.FinancialTransactionRepository;
import dev.ordy.erp.finance.itemPrice.ItemPrice;
import dev.ordy.erp.finance.itemPrice.ItemPriceService;
import dev.ordy.erp.inventory.inventory.Inventory;
import dev.ordy.erp.inventory.inventory.InventoryRepository;
import dev.ordy.erp.inventory.inventoryItem.InventoryItem;
import dev.ordy.erp.inventory.inventoryItem.InventoryItemService;
import dev.ordy.erp.inventory.inventoryRequest.InventoryRequestRepository;
import dev.ordy.erp.inventory.inventoryRequestItem.InventoryRequestItemRepository;
import dev.ordy.erp.inventory.inventoryTransaction.InventoryTransactionRepository;
import dev.ordy.erp.sales.order.ConfirmationState;
import dev.ordy.erp.sales.order.Order;
import dev.ordy.erp.sales.order.OrderService;
import dev.ordy.erp.sales.orderItem.OrderItem;
import dev.ordy.erp.sales.orderItem.OrderItemRepository;
import dev.ordy.erp.supply.supplyRequest.SupplyRequest;
import dev.ordy.erp.supply.supplyRequest.SupplyRequestRepository;
import dev.ordy.erp.supply.supplyRequestItem.SupplyRequestItem;
import dev.ordy.erp.supply.supplyRequestItem.SupplyRequestItemRepository;
import dev.ordy.erp.user_management.user.UserService;
import dev.ordy.erp.business.step.StepService;
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
            FinancialReceiptService financialReceiptService,
            AccountBalanceService accountBalanceService,
            InventoryRepository inventoryRepository,
            InventoryItemService inventoryItemService,
            InventoryRequestRepository inventoryRequestRepository,
            InventoryRequestItemRepository inventoryRequestItemRepository,
            InventoryTransactionRepository inventoryTransactionRepository,
            OrderService orderService,
            OrderItemRepository orderItemRepository,
            SupplyRequestRepository supplyRequestRepository,
            UserService userService,
            StepService stepService,
            StepSetService stepSetService,
            SupplyRequestItemRepository supplyRequestItemRepository,
            BusinessSettingsService businessSettingsService
            ) {

        return args -> loadData(
                accountService,
                itemService,
                itemPriceService,
                financialTransactionRepository,
                financialReceiptService,
                accountBalanceService,
                inventoryRepository,
                inventoryItemService,
                inventoryRequestRepository,
                inventoryRequestItemRepository,
                inventoryTransactionRepository,
                orderService,
                orderItemRepository,
                supplyRequestRepository,
                supplyRequestItemRepository,
                userService,
                businessService,
                stepService ,
                stepSetService,
                businessSettingsService
                );
    }

    @Transactional
    void loadData(
            AccountService accountService,
            ItemService itemService,
            ItemPriceService itemPriceService,
            FinancialTransactionRepository financialTransactionRepository,
            FinancialReceiptService financialReceiptService,
            AccountBalanceService accountBalanceService,
            InventoryRepository inventoryRepository,
            InventoryItemService inventoryItemService,
            InventoryRequestRepository inventoryRequestRepository,
            InventoryRequestItemRepository inventoryRequestItemRepository,
            InventoryTransactionRepository inventoryTransactionRepository,
            OrderService orderService,
            OrderItemRepository orderItemRepository,
            SupplyRequestRepository supplyRequestRepository,
            SupplyRequestItemRepository supplyRequestItemRepository,
            UserService userService,
            BusinessService businessService,
            StepService stepService,
            StepSetService stepSetService,
            BusinessSettingsService businessSettingsService
    ) {

        //user
        userService.createUser("sina","123",null);
        log.info("Preloaded user");
        log.info("Now getting user");
        log.info("user is .."+userService.findByUsername("sina").getUsername());

        // BusinessSettings Entities
        Business business1 = new Business("Gorilla Corporation", "Manufacturer");
        Business business2 = new Business("Wayne Enterprises", "Conglomerate");
        businessService.createBusiness(business1);
        businessService.createBusiness(business2);
        log.info("Preloaded businesses");

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



        // Account Entities
        Account account1 = new Account("John", "Doe", "John Doe", "Customer", business1, AccountType.LEGAL, AccountCategory.CUSTOMER, Gender.MALE);
        Account account2 = new Account("Jane", "Smith", "Jane Smith", "Customer", business2, AccountType.NATURAL, AccountCategory.CUSTOMER, Gender.FEMALE);
        accountService.createAccount(account1);
        accountService.createAccount(account2);
        log.info("Preloaded accounts");


        // Item Entities
        Item item1 = itemService.createItem("Item A", "Role A", business1, InventoryPolicy.FLEXIBLE, ItemType.PRODUCT, Unit.KILOGRAM);
        Item item2 = itemService.createItem("Tomato", "Role B", business2,  InventoryPolicy.FLEXIBLE,ItemType.SERVICE, Unit.KILOGRAM);
        Item item3 = itemService.createItem("Banana", "Role A", business1, InventoryPolicy.FLEXIBLE, ItemType.PRODUCT, Unit.KILOGRAM);
        Item item4 = itemService.createItem("Apple", "Role B", business2,  InventoryPolicy.FLEXIBLE,ItemType.SERVICE, Unit.KILOGRAM);
        Item item5 = itemService.createItem("Grape", "Role A", business1, InventoryPolicy.FLEXIBLE, ItemType.PRODUCT, Unit.KILOGRAM);
        Item item6 = itemService.createItem("Orange", "Role B", business2,  InventoryPolicy.FLEXIBLE,ItemType.SERVICE, Unit.KILOGRAM);
        log.info("Preloaded items");


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
                ConfirmationState.PENDING,
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
                ConfirmationState.PENDING,
                "PayPal",
                "Admin",
                Order.OrderStatus.INVOICE
        );

        orderService.createOrder(order1);
        orderService.createOrder(order2);
        log.info("Preloaded orders");

        Long InventoryItemId1= 1L;
        Long InventoryItemId2=2L;


        InventoryItem inventoryItem1 = inventoryItemService.getInventoryItemById(InventoryItemId1);
        InventoryItem inventoryItem2 = inventoryItemService.getInventoryItemById(InventoryItemId2);

        ItemPrice itemPrice1 = itemPriceService.getItemPriceByInventoryItem(inventoryItem1);
        ItemPrice itemPrice2 = itemPriceService.getItemPriceByInventoryItem(inventoryItem2);


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

        Long orderId1=order1.getId();

        financialReceiptService.createFinancialReceipt(
                account1,
                100.00,
                FinancialReceiptReferenceType.ORDER,
                FinancialStatus.CREDIT,
                orderId1,
                Currency.USD,
                TransactionStatus.COMPLETED
        );
        log.info("Preloaded financialReceipt");


    }
}
