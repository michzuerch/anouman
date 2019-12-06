package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/* @todo fields? */
@Entity(name = "InvoicePosition")
public class InvoicePosition extends AbstractEntity {
    @ManyToOne
    private Invoice invoice;

    private InvoicePosition(Builder builder) {
        setInvoice(builder.invoice);
    }

    public InvoicePosition() {
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public static final class Builder {
        private Invoice invoice;

        public Builder() {
        }

        public Builder invoice(Invoice val) {
            invoice = val;
            return this;
        }

        public InvoicePosition build() {
            return new InvoicePosition(this);
        }
    }
}
