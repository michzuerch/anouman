package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

/**
 * Created by michzuerch on 07.08.15.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Mehrwertsteuercode.findAll", query = "SELECT m FROM Mehrwertsteuercode m"),
        @NamedQuery(name = "Mehrwertsteuercode.findById", query = "SELECT m FROM Mehrwertsteuercode m where m.id = :id")
})
public class Mehrwertsteuercode extends AbstractEntity {
    @Column
    @NotNull
    @NotEmpty
    private String code;

    @Column
    @NotNull
    @NotEmpty
    private String bezeichnung;

    @Column
    @NotNull
    @Digits(integer = 2, fraction = 2, message = "Ungültiges Zahlenformat")
    @DecimalMin(value = "0.1", message = "Minimale Mehrwertsteuer ist 0.1%")
    @DecimalMax(value = "50", message = "Maximale Mehrwertsteuer ist 50%")
    private Float prozent;

    @OneToOne
    private Konto mehrwertsteuerKonto;

    @Column
    private boolean verkauf;

    @OneToMany(mappedBy = "mehrwertsteuercode")
    private Set<Buchung> buchungen;

    @ManyToOne
    private Buchhaltung buchhaltung;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Float getProzent() {
        return prozent;
    }

    public void setProzent(Float prozent) {
        this.prozent = prozent;
    }

    public Konto getMehrwertsteuerKonto() {
        return mehrwertsteuerKonto;
    }

    public void setMehrwertsteuerKonto(Konto mehrwertsteuerKonto) {
        this.mehrwertsteuerKonto = mehrwertsteuerKonto;
    }

    public boolean isVerkauf() {
        return verkauf;
    }

    public void setVerkauf(boolean verkauf) {
        this.verkauf = verkauf;
    }

    public Set<Buchung> getBuchungen() {
        return buchungen;
    }

    public void setBuchungen(Set<Buchung> buchungen) {
        this.buchungen = buchungen;
    }

    public Buchhaltung getBuchhaltung() {
        return buchhaltung;
    }

    public void setBuchhaltung(Buchhaltung buchhaltung) {
        this.buchhaltung = buchhaltung;
    }

    @Override
    public String toString() {
        return "Mehrwertsteuercode{" +
                "code='" + code + '\'' +
                ", bezeichnung='" + bezeichnung + '\'' +
                ", prozent=" + prozent +
                ", mehrwertsteuerKonto=" + mehrwertsteuerKonto +
                ", verkauf=" + verkauf +
                ", buchungen=" + buchungen +
                ", buchhaltung=" + buchhaltung +
                ", version=" + version +
                ", id=" + id +
                '}';
    }
}
