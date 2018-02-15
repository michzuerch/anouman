package com.gmail.michzuerch.anouman.backend.entity;

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
public class Mehrwertsteuercode extends AbstractEntity {
    @Column
    @NotNull
    private String code;

    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @NotNull
    @Digits(integer = 2, fraction = 2, message = "Ungültiges Zahlenformat")
    @DecimalMin(value = "0.1", message = "Minimale Mehrwertsteuer ist 0.1%")
    @DecimalMax(value = "50", message = "Maximale Mehrwertsteuer ist 50%")
    private Float prozent;

    @Column
    private boolean verkauf;

    @OneToMany(mappedBy = "mehrwertsteuercode", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Buchung> buchungen = new ArrayList<>();

    @ManyToOne
    private Buchhaltung buchhaltung;

    @ManyToOne
    private Konto mehrwertsteuerKonto;

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

    public Buchhaltung getBuchhaltung() {
        return buchhaltung;
    }

    public void setBuchhaltung(Buchhaltung buchhaltung) {
        this.buchhaltung = buchhaltung;
    }

    public String getProzentString() {
        return getProzent().toString();
    }

    public void setProzentString(String prozent) {
        setProzent(Float.valueOf(prozent));
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
