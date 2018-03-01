package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.adressen;

import java.util.ArrayList;
import java.util.List;

public class BackupAdresse {
    private String firma;
    private String anrede;
    private String vorname;
    private String nachname;
    private String strasse;
    private String ort;
    private String postleitzahl;
    private Double stundensatz;
    private List<BackupRechnung> rechnungen = new ArrayList<>();


    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(String postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public Double getStundensatz() {
        return stundensatz;
    }

    public void setStundensatz(Double stundensatz) {
        this.stundensatz = stundensatz;
    }

    public List<BackupRechnung> getRechnungen() {
        return rechnungen;
    }

    public void setRechnungen(List<BackupRechnung> rechnungen) {
        this.rechnungen = rechnungen;
    }

    @Override
    public String toString() {
        return "BackupAdresse{" +
                "firma='" + firma + '\'' +
                ", anrede='" + anrede + '\'' +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", strasse='" + strasse + '\'' +
                ", ort='" + ort + '\'' +
                ", postleitzahl='" + postleitzahl + '\'' +
                ", stundensatz=" + stundensatz +
                ", rechnungen=" + rechnungen +
                '}';
    }
}
