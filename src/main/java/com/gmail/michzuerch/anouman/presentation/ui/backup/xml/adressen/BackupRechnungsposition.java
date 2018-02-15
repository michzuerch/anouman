package com.gmail.michzuerch.anouman.presentation.ui.backup.xml.adressen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class BackupRechnungsposition {
    @XmlElement
    private String bezeichnung;
    @XmlElement
    private String bezeichnunglang;
    @XmlElement
    private String mengeneinheit;
    @XmlElement
    private Double stueckpreis;
    @XmlElement
    private Double anzahl;

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnunglang() {
        return bezeichnunglang;
    }

    public void setBezeichnunglang(String bezeichnunglang) {
        this.bezeichnunglang = bezeichnunglang;
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
}
