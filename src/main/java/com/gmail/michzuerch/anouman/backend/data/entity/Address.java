package com.gmail.michzuerch.anouman.backend.data.entity;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity(name = "Address") // "Order" is a reserved word
public class Address extends AbstractEntity {

    private String companyName;
    
    private String salutation;

    private String firstname;
    
    private String lastname;

    private String street;

    private String zipcode;

    private String city;

    private BigDecimal hourlyRate;

     @Transient
    public Integer getIncoicesCount() {
        return 0;
        //return new Integer(getRechnungen().size());
    }

    @Transient
    public Double getVacantPositionsTotal() {
        double total = 0;
        //for (Rechnung rechnung : getRechnungen()) {
        //    if (rechnung.isBezahlt() == false) total = total + rechnung.getRechnungstotal();
        //}
        return new Double(total);
    }


    public Address() {
    }

    public Address(String companyName, String salutation, String firstname, String lastname, String street, String zipcode, String city, BigDecimal hourlyRate) {
        this.companyName = companyName;
        this.salutation = salutation;
        this.firstname = firstname;
        this.lastname = lastname;
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
        this.hourlyRate = hourlyRate;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSalutation() {
        return this.salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BigDecimal getHourlyRate() {
        return this.hourlyRate;
    }

    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Address companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public Address salutation(String salutation) {
        this.salutation = salutation;
        return this;
    }

    public Address firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public Address lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public Address street(String street) {
        this.street = street;
        return this;
    }

    public Address zipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public Address city(String city) {
        this.city = city;
        return this;
    }

    public Address hourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Address)) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(companyName, address.companyName) && Objects.equals(salutation, address.salutation) && Objects.equals(firstname, address.firstname) && Objects.equals(lastname, address.lastname) && Objects.equals(street, address.street) && Objects.equals(zipcode, address.zipcode) && Objects.equals(city, address.city) && Objects.equals(hourlyRate, address.hourlyRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, salutation, firstname, lastname, street, zipcode, city, hourlyRate);
    }

    @Override
    public String toString() {
        return "{" +
            " companyName='" + getCompanyName() + "'" +
            ", salutation='" + getSalutation() + "'" +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", street='" + getStreet() + "'" +
            ", zipcode='" + getZipcode() + "'" +
            ", city='" + getCity() + "'" +
            ", hourlyRate='" + getHourlyRate() + "'" +
            "}";
    }

}

