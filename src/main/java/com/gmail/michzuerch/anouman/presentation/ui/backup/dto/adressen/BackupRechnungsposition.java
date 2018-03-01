package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.adressen;

public class BackupRechnungsposition {
    private String bezeichnung;
    private String bezeichnunglang;
    private String mengeneinheit;
    private Double stueckpreis;
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

    @Override
    public String toString() {
        return "BackupRechnungsposition{" +
                "bezeichnung='" + bezeichnung + '\'' +
                ", bezeichnunglang='" + bezeichnunglang + '\'' +
                ", mengeneinheit='" + mengeneinheit + '\'' +
                ", stueckpreis=" + stueckpreis +
                ", anzahl=" + anzahl +
                '}';
    }
}
