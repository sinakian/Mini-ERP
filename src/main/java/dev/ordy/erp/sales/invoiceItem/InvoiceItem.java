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

    public InvoiceItem(Business business,Invoice invoice, InventoryItem item, double quantity, ItemPrice itemPrice, double pricePerUnit, Unit unit, Currency currency, double totalGrossPrice, double discountCurrency, double discountPercent, double tax, double totalNetPrice, String createdBy) {
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

}
