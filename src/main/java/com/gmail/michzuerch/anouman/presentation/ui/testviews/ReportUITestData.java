package com.gmail.michzuerch.anouman.presentation.ui.testviews;

import java.io.Serializable;

public class ReportUITestData implements Serializable {
    private String vorname;
    private String nachname;
    private String firma;
    private String ort;

    public ReportUITestData(String vorname, String nachname, String firma, String ort) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.firma = firma;
        this.ort = ort;
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

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }
}
