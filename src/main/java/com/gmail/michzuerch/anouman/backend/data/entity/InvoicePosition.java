
package com.gmail.michzuerch.anouman.backend.data.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity(name = "InvoicePosition")
@Data
public class InvoicePosition extends AbstractEntity {
    @ManyToOne
    private Invoice invoice;
}
