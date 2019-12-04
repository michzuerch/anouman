
package com.gmail.michzuerch.anouman.backend.data.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity(name = "Kontogruppe")
@Data
public class Kontogruppe extends AbstractEntity {
    @ManyToOne
    private Address address;
}
