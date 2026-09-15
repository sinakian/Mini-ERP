package dev.ordy.erp.sales.order;

import dev.ordy.erp.common.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class OrderRequest {

    @NotNull
    private Long businessId;

    @NotNull
    private Long customerId;

    @PositiveOrZero
    private double totalLogisticPrice;

    @PositiveOrZero
    private double discountInPercent;

    @PositiveOrZero
    private double discountInCurrency;

    @NotNull
    private Currency currency;

    @NotBlank
    private String paymentType;

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public double getTotalLogisticPrice() {
        return totalLogisticPrice;
    }

    public void setTotalLogisticPrice(double totalLogisticPrice) {
        this.totalLogisticPrice = totalLogisticPrice;
    }

    public double getDiscountInPercent() {
        return discountInPercent;
    }

    public void setDiscountInPercent(double discountInPercent) {
        this.discountInPercent = discountInPercent;
    }

    public double getDiscountInCurrency() {
        return discountInCurrency;
    }

    public void setDiscountInCurrency(double discountInCurrency) {
        this.discountInCurrency = discountInCurrency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}