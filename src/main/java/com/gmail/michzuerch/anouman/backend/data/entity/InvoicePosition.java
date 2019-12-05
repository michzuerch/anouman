package com.gmail.michzuerch.anouman.backend.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "InvoicePosition")
@Data
public class InvoicePosition extends AbstractEntity {
    @ManyToOne
    private Invoice invoice;
}
