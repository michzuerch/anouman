package com.gmail.michzuerch.anouman.backend.jpa.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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

    @OneToMany(mappedBy = "buchhaltung", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Kontoklasse> kontoklasse = new ArrayList<>();

    @OneToMany(mappedBy = "buchhaltung", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mehrwertsteuercode> mehrwertsteuercode = new ArrayList<>();

    @OneToMany(mappedBy = "buchhaltung", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Buchung> buchungs = new ArrayList<>();

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

    public List<Buchung> getBuchungs() {
        return buchungs;
    }

    public void setBuchungs(List<Buchung> buchungs) {
        this.buchungs = buchungs;
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
