package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "Invoice")
public class Invoice extends AbstractEntity {
    @NotNull
    private LocalDate date;

    private String description;

    private int timeForPayment;

    private boolean paid;

    private boolean forwarded;

    @ManyToOne
    private Address address;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<Effort> efforts;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceDetail> invoiceDetails;

    private Invoice(Builder builder) {
        setDate(builder.date);
        setDescription(builder.description);
        setTimeForPayment(builder.timeForPayment);
        setPaid(builder.paid);
        setForwarded(builder.forwarded);
        setAddress(builder.address);
        setEfforts(builder.efforts);
        setInvoiceDetails(builder.invoiceDetails);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTimeForPayment() {
        return timeForPayment;
    }

    public void setTimeForPayment(int timeForPayment) {
        this.timeForPayment = timeForPayment;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isForwarded() {
        return forwarded;
    }

    public void setForwarded(boolean forwarded) {
        this.forwarded = forwarded;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Effort> getEfforts() {
        return efforts;
    }

    public void setEfforts(List<Effort> efforts) {
        this.efforts = efforts;
    }

    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }


    public static final class Builder {
        private @NotNull LocalDate date;
        private String description;
        private int timeForPayment;
        private boolean paid;
        private boolean forwarded;
        private Address address;
        private List<Effort> efforts;
        private List<InvoiceDetail> invoiceDetails;

        public Builder() {
        }

        public Builder date(@NotNull LocalDate val) {
            date = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder timeForPayment(int val) {
            timeForPayment = val;
            return this;
        }

        public Builder paid(boolean val) {
            paid = val;
            return this;
        }

        public Builder forwarded(boolean val) {
            forwarded = val;
            return this;
        }

        public Builder address(Address val) {
            address = val;
            return this;
        }

        public Builder efforts(List<Effort> val) {
            efforts = val;
            return this;
        }

        public Builder invoiceDetails(List<InvoiceDetail> val) {
            invoiceDetails = val;
            return this;
        }

        public Invoice build() {
            return new Invoice(this);
        }
    }
}
