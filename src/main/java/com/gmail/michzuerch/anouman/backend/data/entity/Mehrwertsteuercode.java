
package com.gmail.michzuerch.anouman.backend.data.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity(name = "Mehrwertsteuercode") 
public class Mehrwertsteuercode extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Bookkeeping bookkeeping;

}
