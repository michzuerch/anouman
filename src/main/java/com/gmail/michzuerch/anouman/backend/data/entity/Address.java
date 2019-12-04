package com.gmail.michzuerch.anouman.backend.data.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import lombok.Data;

@Entity(name = "Address")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "AddressHasInvoices", attributeNodes = { @NamedAttributeNode("invoices") }) })
@Data
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
}
