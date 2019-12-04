
package com.gmail.michzuerch.anouman.backend.data.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity(name = "Kontoklasse") 
@Data
public class Kontoklasse extends AbstractEntity {
    @ManyToOne
    private Bookkeeping bookkeeping;
}
