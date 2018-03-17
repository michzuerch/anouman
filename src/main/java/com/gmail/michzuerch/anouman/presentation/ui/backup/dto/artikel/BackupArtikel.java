package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.artikel;

import java.util.ArrayList;
import java.util.List;

public class BackupArtikel {
    private List<BackupArtikelbild> backupArtikelbilds = new ArrayList<>();
    private String bezeichnung;
    private String bezeichnungLang;
    private String mengeneinheit;
    private Double anzahl;
    private Double stueckpreis;


    public List<BackupArtikelbild> getBackupArtikelbilds() {
        return backupArtikelbilds;
    }

    public void setBackupArtikelbilds(List<BackupArtikelbild> backupArtikelbilds) {
        this.backupArtikelbilds = backupArtikelbilds;
    }

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

    @Override
    public String toString() {
        return "BackupArtikel{" +
                "backupArtikelbilds=" + backupArtikelbilds +
                '}';
    }
}
