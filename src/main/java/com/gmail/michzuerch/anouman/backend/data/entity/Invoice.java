package com.gmail.michzuerch.anouman.backend.data.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "Invoice")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "InvoiceHasEfforts", attributeNodes = {@NamedAttributeNode("efforts")})})
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
