package dev.ordy.erp.sales.orderStep;

import dev.ordy.erp.sales.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderStepRepository extends JpaRepository<OrderStep, Long> {
    List<OrderStep> findByOrderId(Long orderId);
}
