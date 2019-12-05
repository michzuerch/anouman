package com.gmail.michzuerch.anouman.backend.data.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "InvoicePosition")
@Data
@Builder
public class InvoicePosition extends AbstractEntity {
    @ManyToOne
    private Invoice invoice;
}
