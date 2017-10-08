package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Created by michzuerch on 03.03.15.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Rechnungsposition.findAll", query = "SELECT rp FROM Rechnungsposition rp"),
        @NamedQuery(name = "Rechnungsposition.findById", query = "SELECT rp FROM Rechnungsposition rp where rp.id = :id"),
        @NamedQuery(name = "Rechnungsposition.findByRechnung", query = "SELECT rp FROM Rechnungsposition rp where rp.rechnung.id = :rechnungId"),
        @NamedQuery(name = "Rechnungsposition.findByBezeichnung", query = "SELECT rp FROM Rechnungsposition rp where rp.bezeichnung LIKE :bezeichnung")
})
public class Rechnungsposition extends AbstractEntity {
    @Column
    @NotNull
    @Size(min = 5)
    private String bezeichnung;

    @Column
    private String bezeichnunglang;

    @Column
    @NotNull
    private String mengeneinheit;

    @Column
    @Digits(integer = 8, fraction = 2, message = "Muss ein gültiger Betrag sein")
    private Double stueckpreis;

    @Column
    private Double anzahl;

    @ManyToOne
    private Rechnung rechnung;

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnunglang() {
        return bezeichnunglang;
    }

    public void setBezeichnunglang(String bezeichnungLang) {
        this.bezeichnunglang = bezeichnungLang;
    }

    public String getMengeneinheit() {
        return mengeneinheit;
    }

    public void setMengeneinheit(String mengeneinheit) {
        this.mengeneinheit = mengeneinheit;
    }

    public Double getStueckpreis() {
        return stueckpreis;
    }

    public void setStueckpreis(Double stueckpreis) {
        this.stueckpreis = stueckpreis;
    }

    public Double getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(Double anzahl) {
        this.anzahl = anzahl;
    }

    public Rechnung getRechnung() {
        return rechnung;
    }

    public void setRechnung(Rechnung rechnung) {
        this.rechnung = rechnung;
    }

    @Transient
    public Double getPositionstotal() {
        BigDecimal stk = new BigDecimal(getStueckpreis());
        BigDecimal anz = new BigDecimal(getAnzahl());
        BigDecimal total = stk.multiply(anz);
        return total.doubleValue();
    }

    @Override
    public String toString() {
        return "Rechnungsposition{" +
                "bezeichnung='" + bezeichnung + '\'' +
                ", bezeichnunglang='" + bezeichnunglang + '\'' +
                ", mengeneinheit='" + mengeneinheit + '\'' +
                ", stueckpreis=" + stueckpreis +
                ", anzahl=" + anzahl +
                ", rechnung=" + rechnung +
                '}';
    }
}
