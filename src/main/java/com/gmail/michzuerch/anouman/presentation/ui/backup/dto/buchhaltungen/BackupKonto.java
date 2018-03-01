package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.buchhaltungen;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by michzuerch on 16.11.15.
 */

public class BackupKonto {
    private String bezeichnung;
    private String bemerkung;
    private String kontonummer;
    private String showKontonummer;
    private Double anfangsbestand;
    private Long id;
    private Set<BackupBuchung> buchungen = new HashSet<>();
    private Set<BackupMehrwertsteuercode> mehrwertsteuercodes = new HashSet<>();

    public BackupKonto() {
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBemerkung() {
        return bemerkung;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(String kontonummer) {
        this.kontonummer = kontonummer;
    }

    public Double getAnfangsbestand() {
        return anfangsbestand;
    }

    public void setAnfangsbestand(Double anfangsbestand) {
        this.anfangsbestand = anfangsbestand;
    }

    public String getShowKontonummer() {
        return showKontonummer;
    }

    public void setShowKontonummer(String showKontonummer) {
        this.showKontonummer = showKontonummer;
    }

    public Set<BackupBuchung> getBuchungen() {
        return buchungen;
    }

    public void setBuchungen(Set<BackupBuchung> buchungen) {
        this.buchungen = buchungen;
    }

    public Set<BackupMehrwertsteuercode> getMehrwertsteuercodes() {
        return mehrwertsteuercodes;
    }

    public void setMehrwertsteuercodes(Set<BackupMehrwertsteuercode> mehrwertsteuercodes) {
        this.mehrwertsteuercodes = mehrwertsteuercodes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
