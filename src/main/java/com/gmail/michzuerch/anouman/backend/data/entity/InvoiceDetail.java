package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity(name = "InvoicePosition")
public class InvoiceDetail extends AbstractEntity {
    @NotNull
    @NotEmpty
    private String decription;

    private String descriptionLong;

    private String quantityUnit;

    private BigDecimal quantity;

    private BigDecimal unitPrice;

    @ManyToOne
    private Invoice invoice;

    private InvoiceDetail(Builder builder) {
        setDecription(builder.decription);
        setDescriptionLong(builder.descriptionLong);
        setQuantityUnit(builder.quantityUnit);
        setQuantity(builder.quantity);
        setUnitPrice(builder.unitPrice);
        setInvoice(builder.invoice);
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getDescriptionLong() {
        return descriptionLong;
    }

    public void setDescriptionLong(String descriptionLong) {
        this.descriptionLong = descriptionLong;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }


    public static final class Builder {
        private @NotNull @NotEmpty String decription;
        private String descriptionLong;
        private String quantityUnit;
        private BigDecimal quantity;
        private BigDecimal unitPrice;
        private Invoice invoice;

        public Builder() {
        }

        public Builder decription(@NotNull @NotEmpty String val) {
            decription = val;
            return this;
        }

        public Builder descriptionLong(String val) {
            descriptionLong = val;
            return this;
        }

        public Builder quantityUnit(String val) {
            quantityUnit = val;
            return this;
        }

        public Builder quantity(BigDecimal val) {
            quantity = val;
            return this;
        }

        public Builder unitPrice(BigDecimal val) {
            unitPrice = val;
            return this;
        }

        public Builder invoice(Invoice val) {
            invoice = val;
            return this;
        }

        public InvoiceDetail build() {
            return new InvoiceDetail(this);
        }
    }
}
