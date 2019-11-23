package com.gmail.michzuerch.anouman.backend.data.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity(name = "Invoice") // "Order" is a reserved word
public class Invoice extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @NotNull
    private LocalDate date;

    private String description;

    private int timeForPayment;

    private boolean paid;

    private boolean forwarded;

    @ManyToOne
    private Address address;


    public Invoice() {
    }

    public Invoice(LocalDate date, String description, int timeForPayment, boolean paid, boolean forwarded, Address address) {
        this.date = date;
        this.description = description;
        this.timeForPayment = timeForPayment;
        this.paid = paid;
        this.forwarded = forwarded;
        this.address = address;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTimeForPayment() {
        return this.timeForPayment;
    }

    public void setTimeForPayment(int timeForPayment) {
        this.timeForPayment = timeForPayment;
    }

    public boolean isPaid() {
        return this.paid;
    }

    public boolean getPaid() {
        return this.paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isForwarded() {
        return this.forwarded;
    }

    public boolean getForwarded() {
        return this.forwarded;
    }

    public void setForwarded(boolean forwarded) {
        this.forwarded = forwarded;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Invoice date(LocalDate date) {
        this.date = date;
        return this;
    }

    public Invoice description(String description) {
        this.description = description;
        return this;
    }

    public Invoice timeForPayment(int timeForPayment) {
        this.timeForPayment = timeForPayment;
        return this;
    }

    public Invoice paid(boolean paid) {
        this.paid = paid;
        return this;
    }

    public Invoice forwarded(boolean forwarded) {
        this.forwarded = forwarded;
        return this;
    }

    public Invoice address(Address address) {
        this.address = address;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Invoice)) {
            return false;
        }
        Invoice invoice = (Invoice) o;
        return Objects.equals(date, invoice.date) && Objects.equals(description, invoice.description) && timeForPayment == invoice.timeForPayment && paid == invoice.paid && forwarded == invoice.forwarded && Objects.equals(address, invoice.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, description, timeForPayment, paid, forwarded, address);
    }

    @Override
    public String toString() {
        return "{" +
            " date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", timeForPayment='" + getTimeForPayment() + "'" +
            ", paid='" + isPaid() + "'" +
            ", forwarded='" + isForwarded() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }

}
