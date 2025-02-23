package dev.ordy.erp.sales.order;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;

    public OrderService(OrderRepository orderRepository,
                        ApplicationEventPublisher eventPublisher
                        ) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Transactional
    public Order createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        eventPublisher.publishEvent(new OrderCreateEvent(this, savedOrder));
        return savedOrder;
    }

    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    public void changeStatusToInvoice(Long orderId){
        Order order = getOrderById(orderId);
        order.setOrderStatus(Order.OrderStatus.INVOICE);
        orderRepository.save(order);
    }
}
