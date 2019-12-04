package com.gmail.michzuerch.anouman.backend.data.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity(name = "Invoice")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "InvoiceHasEfforts", attributeNodes = { @NamedAttributeNode("efforts") }) })
@Data
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

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<Effort> efforts;

}
