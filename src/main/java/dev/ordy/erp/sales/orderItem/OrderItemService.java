package dev.ordy.erp.sales.orderItem;

import dev.ordy.erp.finance.itemPrice.ItemPrice;
import dev.ordy.erp.finance.itemPrice.ItemPriceService;
import dev.ordy.erp.finance.tax.Tax;
import dev.ordy.erp.finance.tax.TaxService;
import dev.ordy.erp.sales.order.OrderItemChangeEvent;
import dev.ordy.erp.business.business_settings.BusinessSettings;
import dev.ordy.erp.business.business_settings.BusinessSettingsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dev.ordy.erp.sales.order.Order;
import dev.ordy.erp.sales.order.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ItemPriceService itemPriceService;
    private final TaxService taxService;
    private final BusinessSettingsService businessSettingsService;
    private final ApplicationEventPublisher eventPublisher;

    public OrderItemService(OrderItemRepository orderItemRepository,
                            OrderRepository orderRepository,
                            ItemPriceService itemPriceService,
                            TaxService taxService,
                            BusinessSettingsService businessSettingsService,
                            ApplicationEventPublisher eventPublisher
    ) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.itemPriceService = itemPriceService;
        this.taxService = taxService;
        this.businessSettingsService = businessSettingsService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(readOnly = true)
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<OrderItem> getOrderItemsByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }

    @Transactional(readOnly = true)
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        // Find the order first
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Return items associated with this order
        return orderItemRepository.findByOrder(order);
    }

    @Transactional
    public OrderItem createOrderItem(OrderItem orderItem) {
        // Prepare the order item by resolving price and tax values
        prepareOrderItem(orderItem);

        OrderItem savedItem = orderItemRepository.save(orderItem);
        eventPublisher.publishEvent(new OrderItemChangeEvent(savedItem.getOrder().getId()));
        return savedItem;
    }

    @Transactional
    public void deleteOrderItem(Long id) {
        OrderItem itemToDelete = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(id));
        Long orderId = itemToDelete.getOrder().getId();
        orderItemRepository.deleteById(id);
        eventPublisher.publishEvent(new OrderItemChangeEvent(orderId));
    }

    @Transactional
    public List<OrderItem> createOrderItems(List<OrderItem> orderItems) {
        // Prepare all order items by resolving prices and taxes
        orderItems.forEach(this::prepareOrderItem);

        List<OrderItem> savedItems = orderItemRepository.saveAll(orderItems);

        // Group by order ID and publish events for each affected order
        savedItems.stream()
                .map(item -> item.getOrder().getId())
                .distinct()
                .forEach(orderId -> eventPublisher.publishEvent(new OrderItemChangeEvent(orderId)));

        return savedItems;
    }

    /**
     * Prepare the order item by resolving prices and taxes and recalculating totals
     */
    private void prepareOrderItem(OrderItem orderItem) {
        // Apply default tax if no tax is specified and no custom tax rate is defined
        applyDefaultTaxIfNeeded(orderItem);

        // Continue with regular preparation
        orderItem.prepare();
    }

    /**
     * Apply the default tax from business settings if no tax is specified
     * and no custom tax rate is defined
     */
    private void applyDefaultTaxIfNeeded(OrderItem orderItem) {
        // Only apply default tax if both tax and customTaxRate are not specified
        if (orderItem.getTax() == null && orderItem.getCustomTaxRate() == null) {
            Long businessId = orderItem.getBusiness().getId();
            try {
                BusinessSettings businessSettings = businessSettingsService.getDefaultSettings(businessId);
                Tax defaultTax = businessSettings.getDefaultTax();

                if (defaultTax != null) {
                    orderItem.setTax(defaultTax);
                }
            } catch (Exception e) {
                // Log error but continue, as this is not critical
                // Logger.error("Failed to get default tax for business: " + businessId, e);
            }
        }
    }

    /**
     * Updates only the quantity of an order item and recalculates totals
     */
    @Transactional
    public OrderItem updateOrderItemQuantity(Long id, double newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(id));

        // Update quantity
        orderItem.setQuantity(newQuantity);
        orderItem.recalculateTotals();

        // Save and return updated item
        OrderItem savedItem = orderItemRepository.save(orderItem);

        // Publish event after update
        eventPublisher.publishEvent(new OrderItemChangeEvent(savedItem.getOrder().getId()));

        return savedItem;
    }

    /**
     * Update an entire order item
     */
    @Transactional
    public OrderItem updateOrderItem(Long id, OrderItem updatedOrderItem) {
        OrderItem existingOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(id));

        existingOrderItem = updateOrderItemFields(existingOrderItem, updatedOrderItem);

        // Apply default tax if needed
        if (existingOrderItem.getTax() == null && existingOrderItem.getCustomTaxRate() == null) {
            applyDefaultTaxIfNeeded(existingOrderItem);
        }

        // Prepare the updated item
        existingOrderItem.prepare();

        OrderItem savedItem = orderItemRepository.save(existingOrderItem);

        // Publish event after update
        eventPublisher.publishEvent(new OrderItemChangeEvent(savedItem.getOrder().getId()));

        return savedItem;
    }

    /**
     * Helper method to update order item fields
     */
    private OrderItem updateOrderItemFields(OrderItem existingItem, OrderItem updatedItem) {
        return new OrderItem.Builder(existingItem)
                .withItem(updatedItem.getItem() != null ? updatedItem.getItem() : existingItem.getItem())
                .withQuantity(updatedItem.getQuantity())
                .withItemPrice(updatedItem.getItemPrice() != null ? updatedItem.getItemPrice() : existingItem.getItemPrice())
                .withCustomPricePerUnit(updatedItem.getCustomPricePerUnit())
                .withPricePerUnit(updatedItem.getPricePerUnit())
                .withUnit(updatedItem.getUnit() != null ? updatedItem.getUnit() : existingItem.getUnit())
                .withCurrency(updatedItem.getCurrency() != null ? updatedItem.getCurrency() : existingItem.getCurrency())
                .withTax(updatedItem.getTax() != null ? updatedItem.getTax() : existingItem.getTax())
                .withCustomTaxRate(updatedItem.getCustomTaxRate())
                .withTaxRate(updatedItem.getTaxRate())
                .withTotalGrossPrice(updatedItem.getTotalGrossPrice())
                .withDiscountCurrency(updatedItem.getDiscountCurrency())
                .withDiscountPercent(updatedItem.getDiscountPercent())
                .withTaxAmount(updatedItem.getTaxAmount())
                .withTotalNetPrice(updatedItem.getTotalNetPrice())
                .build();
    }
}