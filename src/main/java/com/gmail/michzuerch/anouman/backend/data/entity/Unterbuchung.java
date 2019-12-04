
package com.gmail.michzuerch.anouman.backend.data.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity(name = "Unterbuchung")
@Data
public class Unterbuchung extends AbstractEntity {
    @NotNull
    private String buchungstext;

    private LocalDate buchungsdatum;

    private Float betrag;

    @ManyToOne
    @JoinColumn(name = "KONTOSOLL_ID", nullable = false)
    private Konto kontoSoll;

    @ManyToOne
    @JoinColumn(name = "KONTOHABEN_ID", nullable = false)
    private Konto kontoHaben;

    @ManyToOne
    @JoinColumn(name = "BUCHUNG_ID", nullable = false)
    private Buchung buchung;
}
