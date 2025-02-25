package dev.ordy.erp.sales.orderStep;

import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.business.step.Step;
import dev.ordy.erp.sales.order.Order;
import dev.ordy.erp.business.step.StepService;
import dev.ordy.erp.business.business.BusinessService;
import dev.ordy.erp.sales.order.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderStepService {

    private final OrderStepRepository orderStepRepository;
    private final StepService stepService;
    private final BusinessService businessService;
    private final OrderService orderService;

    public OrderStepService(OrderStepRepository orderStepRepository,
                            StepService stepService,
                            BusinessService businessService,
                            OrderService orderService
                            ) {
        this.orderStepRepository = orderStepRepository;
        this.stepService = stepService;
        this.businessService = businessService;
        this.orderService = orderService;
    }

    @Transactional
    public OrderStep markStepAsCompleted(Long stepId) {
        OrderStep orderStep = orderStepRepository.findById(stepId)
                .orElseThrow(() -> new EntityNotFoundException("Order step not found with id: " + stepId));

        orderStep.setIsCompleted(true);
        return orderStepRepository.save(orderStep);
    }

    @Transactional
    public OrderStep markStepAsIncomplete(Long stepId) {
        OrderStep orderStep = orderStepRepository.findById(stepId)
                .orElseThrow(() -> new EntityNotFoundException("Order step not found with id: " + stepId));

        orderStep.setIsCompleted(false);
        return orderStepRepository.save(orderStep);
    }


    @Transactional(readOnly = true)
    public List<OrderStep> getAllOrderSteps() {
        return orderStepRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<OrderStep> getOrderStepById(Long id) {
        return orderStepRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<OrderStep> getOrderStepsByOrderId(Long orderId) {
        return orderStepRepository.findByOrderId(orderId);
    }

    @Transactional
    public OrderStep createOrderStep(OrderStep orderStep) {
        return orderStepRepository.save(orderStep);
    }

    @Transactional
    public void deleteOrderStep(Long id) {
        orderStepRepository.deleteById(id);
    }

    @Transactional
    public List<OrderStep> createOrderSteps(Long businessId, Long orderId, Long stepSetId) {
        // Fetch business and order to ensure they exist
        Business business = businessService.getBusinessById(businessId)
                .orElseThrow(() -> new EntityNotFoundException("BusinessSettings not found"));

        Order order = orderService.getOrderById(orderId);

        // Fetch steps by stepSetId
        List<Step> steps = stepService.getStepsByStepSetId(stepSetId);

        if (steps.isEmpty()) {
            throw new IllegalArgumentException("No steps found for the given StepSet");
        }

        // Create and save OrderSteps
        List<OrderStep> orderSteps = steps.stream().map(step -> {
            OrderStep orderStep = new OrderStep();
            orderStep.setBusiness(business);
            orderStep.setOrder(order);
            orderStep.setStep(step);
            orderStep.setIsCompleted(false);
            return orderStep;
        }).collect(Collectors.toList());

        return orderStepRepository.saveAll(orderSteps);
    }
}
