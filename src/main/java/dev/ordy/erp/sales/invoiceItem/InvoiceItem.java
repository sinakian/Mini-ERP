package dev.ordy.erp.sales.invoiceItem;

import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.common.Currency;
import dev.ordy.erp.common.Unit;
import dev.ordy.erp.finance.itemPrice.ItemPrice;
import dev.ordy.erp.inventory.inventoryItem.InventoryItem;
import dev.ordy.erp.sales.invoice.Invoice;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "INVOICE_ITEM")
@EntityListeners(AuditingEntityListener.class)
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private InventoryItem item;

    private double quantity;

    @ManyToOne
    @JoinColumn(name = "item_price_id", nullable = false)
    private ItemPrice itemPrice;

    private double pricePerUnit;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private double totalGrossPrice;
    private double discountCurrency;
    private double discountPercent;
    private double tax;
    private double totalNetPrice;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    public InvoiceItem() {}

    public InvoiceItem(Business business, Invoice invoice, InventoryItem item, double quantity, ItemPrice itemPrice, double pricePerUnit, Unit unit, Currency currency, double totalGrossPrice, double discountCurrency, double discountPercent, double tax, double totalNetPrice, String createdBy) {
        this.business = business;
        this.invoice = invoice;
        this.item = item;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.pricePerUnit = pricePerUnit;
        this.unit = unit;
        this.currency = currency;
        this.totalGrossPrice = totalGrossPrice;
        this.discountCurrency = discountCurrency;
        this.discountPercent = discountPercent;
        this.tax = tax;
        this.totalNetPrice = totalNetPrice;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public InventoryItem getItem() {
        return item;
    }

    public void setItem(InventoryItem item) {
        this.item = item;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public ItemPrice getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(ItemPrice itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getTotalGrossPrice() {
        return totalGrossPrice;
    }

    public void setTotalGrossPrice(double totalGrossPrice) {
        this.totalGrossPrice = totalGrossPrice;
    }

    public double getDiscountCurrency() {
        return discountCurrency;
    }

    public void setDiscountCurrency(double discountCurrency) {
        this.discountCurrency = discountCurrency;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotalNetPrice() {
        return totalNetPrice;
    }

    public void setTotalNetPrice(double totalNetPrice) {
        this.totalNetPrice = totalNetPrice;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}

