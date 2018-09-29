package com.gmail.michzuerch.anouman.backend.jpa.domain;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Artikel extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    private String bezeichnungLang;

    @ManyToOne
    private Artikelkategorie artikelkategorie;

    @OneToMany(mappedBy = "artikel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Artikelbild> artikelbilds = new ArrayList<>();

    @Column
    @NotNull
    private String mengeneinheit;

    @Column
    @NotNull
    private Double anzahl;

    @Column
    @Digits(integer = 8, fraction = 2, message = "Muss ein gültiger Betrag sein")
    private Double stueckpreis;

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnungLang() {
        return bezeichnungLang;
    }

    public void setBezeichnungLang(String bezeichnungLang) {
        this.bezeichnungLang = bezeichnungLang;
    }

    public Artikelkategorie getArtikelkategorie() {
        return artikelkategorie;
    }

    public void setArtikelkategorie(Artikelkategorie artikelkategorie) {
        this.artikelkategorie = artikelkategorie;
    }

    public List<Artikelbild> getArtikelbilds() {
        return artikelbilds;
    }

    public void setArtikelbilds(List<Artikelbild> artikelbilds) {
        this.artikelbilds = artikelbilds;
    }

    public String getMengeneinheit() {
        return mengeneinheit;
    }

    public void setMengeneinheit(String mengeneinheit) {
        this.mengeneinheit = mengeneinheit;
    }

    public Double getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(Double anzahl) {
        this.anzahl = anzahl;
    }

    public Double getStueckpreis() {
        return stueckpreis;
    }

    public void setStueckpreis(Double stueckpreis) {
        this.stueckpreis = stueckpreis;
    }
}
