package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

@Entity(name = "Address")
public class Address extends AbstractEntity {
    private String companyName;

    private String salutation;

    private String firstname;

    private String lastname;

    private String street;

    private String zipcode;

    private String city;

    private BigDecimal hourlyRate;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<Invoice> invoices;

    private Address(Builder builder) {
        setCompanyName(builder.companyName);
        setSalutation(builder.salutation);
        setFirstname(builder.firstname);
        setLastname(builder.lastname);
        setStreet(builder.street);
        setZipcode(builder.zipcode);
        setCity(builder.city);
        setHourlyRate(builder.hourlyRate);
        setInvoices(builder.invoices);
    }

    @Transient
    public Integer getIncoicesCount() {
        return 0;
        // return new Integer(getRechnungen().size());
    }

    @Transient
    public Double getVacantPositionsTotal() {
        double total = 0;
        // for (Rechnung rechnung : getRechnungen()) {
        // if (rechnung.isBezahlt() == false) total = total +
        // rechnung.getRechnungstotal();
        // }
        return Double.valueOf(total);
    }

    public Address() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public static final class Builder {
        private String companyName;
        private String salutation;
        private String firstname;
        private String lastname;
        private String street;
        private String zipcode;
        private String city;
        private BigDecimal hourlyRate;
        private List<Invoice> invoices;

        public Builder() {
        }

        public Builder companyName(String val) {
            companyName = val;
            return this;
        }

        public Builder salutation(String val) {
            salutation = val;
            return this;
        }

        public Builder firstname(String val) {
            firstname = val;
            return this;
        }

        public Builder lastname(String val) {
            lastname = val;
            return this;
        }

        public Builder street(String val) {
            street = val;
            return this;
        }

        public Builder zipcode(String val) {
            zipcode = val;
            return this;
        }

        public Builder city(String val) {
            city = val;
            return this;
        }

        public Builder hourlyRate(BigDecimal val) {
            hourlyRate = val;
            return this;
        }

        public Builder invoices(List<Invoice> val) {
            invoices = val;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
