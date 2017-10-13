package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michzuerch on 07.08.15.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Konto.findAll", query = "SELECT k FROM Konto k"),
        @NamedQuery(name = "Konto.findById", query = "SELECT k FROM Konto k where k.id = :id")
})
public class Konto extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @NotNull
    private String kontonummer;

    @Column
    private String bemerkung;

    @OneToMany(mappedBy = "kontoSoll", cascade = CascadeType.ALL)
    private List<Unterbuchung> soll = new ArrayList<>();

    @OneToMany(mappedBy = "kontoHaben", cascade = CascadeType.ALL)
    private List<Unterbuchung> haben = new ArrayList<>();

    @ManyToOne
    private Kontoart kontoart;

    @Column
    private Double anfangsbestand;

    @Transient
    public String getShowKontonummer() {
        return getKontoart().getShowKontonummer() + getKontonummer();
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(String kontonummer) {
        this.kontonummer = kontonummer;
    }

    public Kontoart getKontoart() {
        return kontoart;
    }

    public void setKontoart(Kontoart kontoart) {
        this.kontoart = kontoart;
    }

    public Double getAnfangsbestand() {
        return anfangsbestand;
    }

    public void setAnfangsbestand(Double anfangsbestand) {
        this.anfangsbestand = anfangsbestand;
    }

    public List<Unterbuchung> getSoll() {
        return soll;
    }

    public void setSoll(List<Unterbuchung> soll) {
        this.soll = soll;
    }

    public List<Unterbuchung> getHaben() {
        return haben;
    }

    public void setHaben(List<Unterbuchung> haben) {
        this.haben = haben;
    }

    public String getBemerkung() {
        return bemerkung;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

    @Override
    public String toString() {
        return "Konto{" +
                "bezeichnung='" + bezeichnung + '\'' +
                "bemerkung='" + bemerkung + '\'' +
                ", kontonummer='" + kontonummer + '\'' +
                ", soll=" + soll +
                ", haben=" + haben +
                ", kontoart=" + kontoart +
                ", anfangsbestand=" + anfangsbestand +
                '}';
    }
}
