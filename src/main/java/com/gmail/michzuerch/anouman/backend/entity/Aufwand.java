package com.gmail.michzuerch.anouman.backend.entity;

/**
 * Created by michzuerch on 06.07.15.
 */

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@NamedQueries({
        @NamedQuery(name = "Aufwand.findAll", query = "SELECT a FROM Aufwand a order by a.start"),
        @NamedQuery(name = "Aufwand.findById", query = "SELECT a FROM Aufwand a where a.id = :id"),
        @NamedQuery(name = "Aufwand.findByTitel", query = "SELECT a FROM Aufwand a where a.titel LIKE :titel")
})
public class Aufwand extends AbstractEntity {
    @Column
    @NotNull
    private String titel;

    @Column
    private String bezeichnung;

    @Column(name = "startzeit")
    private LocalDateTime start;

    @Column(name = "endzeit")
    private LocalDateTime end;

    @ManyToOne
    private Rechnung rechnung;

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Rechnung getRechnung() {
        return rechnung;
    }

    public void setRechnung(Rechnung rechnung) {
        this.rechnung = rechnung;
    }

    @Transient
    public Long getDauerInMinuten() {

        return Duration.between(start, end).toMinutes();
    }

    @Transient
    public Long getDauerInStunden() {

        return Duration.between(start, end).toHours();
    }

    @Transient
    public Double getPositionstotal() {
        BigDecimal stk = null;
        if (getRechnung() != null) {
            stk = new BigDecimal(getRechnung().getAdresse().getStundensatz());
        } else {
            stk = new BigDecimal(130);
        }
        BigDecimal anz = new BigDecimal(getDauerInStunden());
        BigDecimal total = stk.multiply(anz);
        return total.doubleValue();
    }

    @Override
    public String toString() {
        return "Aufwand{" +
                ", id=" + id +
                "titel='" + titel + '\'' +
                ", bezeichnung='" + bezeichnung + '\'' +
                ", start=" + start +
                ", ende=" + end +
                ", rechnung=" + rechnung +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Aufwand aufwand = (Aufwand) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(id, aufwand.titel)
                .append(titel, aufwand.titel)
                .append(bezeichnung, aufwand.bezeichnung)
                .append(start, aufwand.start)
                .append(end, aufwand.end)
                .append(rechnung, aufwand.rechnung)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(id)
                .append(titel)
                .append(bezeichnung)
                .append(start)
                .append(end)
                .append(rechnung)
                .toHashCode();
    }
}
