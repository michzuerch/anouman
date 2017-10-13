package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michzuerch on 07.08.15.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Buchhaltung.findAll", query = "SELECT b FROM Buchhaltung b"),
        @NamedQuery(name = "Buchhaltung.findById", query = "SELECT b FROM Buchhaltung b where b.id = :id")

})
public class Buchhaltung extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @NotNull
    @Digits(integer = 4, fraction = 0, message = "Ungültiges Zahlenformat")
    @DecimalMin(value = "1950", message = "Nicht vor 1950")
    @DecimalMax(value = "2150", message = "Nicht nach 2150")
    private int jahr;

    @OneToMany(mappedBy = "buchhaltung", cascade = CascadeType.ALL)
    private List<Kontoklasse> kontoklasse = new ArrayList<>();

    @OneToMany(mappedBy = "buchhaltung", cascade = CascadeType.ALL)
    private List<Mehrwertsteuercode> mehrwertsteuercode = new ArrayList<>();


    public Buchhaltung() {
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public int getJahr() {
        return jahr;
    }

    public void setJahr(int jahr) {
        this.jahr = jahr;
    }

    public List<Kontoklasse> getKontoklasse() {
        return kontoklasse;
    }

    public void setKontoklasse(List<Kontoklasse> kontoklasse) {
        this.kontoklasse = kontoklasse;
    }

    public List<Mehrwertsteuercode> getMehrwertsteuercode() {
        return mehrwertsteuercode;
    }

    public void setMehrwertsteuercode(List<Mehrwertsteuercode> mehrwertsteuercode) {
        this.mehrwertsteuercode = mehrwertsteuercode;
    }

    @Override
    public String toString() {
        return "Buchhaltung{" +
                "bezeichnung='" + bezeichnung + '\'' +
                ", jahr=" + jahr +
                ", kontoklasse=" + kontoklasse +
                ", mehrwertsteuercode=" + mehrwertsteuercode +
                '}';
    }
}
