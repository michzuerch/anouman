package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michzuerch on 07.08.15.
 */
@Entity
public class Konto extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @NotNull
    private String kontonummer;

    @Column
    private String bemerkung;

    @OneToMany(mappedBy = "kontoSoll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Unterbuchung> soll = new ArrayList<>();

    @OneToMany(mappedBy = "kontoHaben", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Unterbuchung> haben = new ArrayList<>();

    @ManyToOne
    private Kontogruppe kontogruppe;

    @OneToMany(mappedBy = "mehrwertsteuerKonto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mehrwertsteuercode> mehrwertsteuercode = new ArrayList<>();

    @Column
    private Double anfangsbestand;

    @Transient
    public String getShowKontonummer() {
        return getKontogruppe().getShowKontonummer() + getKontonummer();
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

    public Kontogruppe getKontogruppe() {
        return kontogruppe;
    }

    public void setKontogruppe(Kontogruppe kontogruppe) {
        this.kontogruppe = kontogruppe;
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

    public List<Mehrwertsteuercode> getMehrwertsteuercode() {
        return mehrwertsteuercode;
    }

    public void setMehrwertsteuercode(List<Mehrwertsteuercode> mehrwertsteuercode) {
        this.mehrwertsteuercode = mehrwertsteuercode;
    }
}
