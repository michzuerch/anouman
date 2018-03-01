package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.buchhaltungen;


import java.time.LocalDate;

/**
 * Created by michzuerch on 16.11.15.
 */
public class BackupBuchung {
    private String buchungstext;

    private LocalDate buchungsdatum;

    private Integer laufnummer;

    private Float betrag;

    private Long kontoSoll;

    private Long kontoHaben;

    private Long id;

    private Long mehrwertsteuercode;

    public BackupBuchung() {
    }

    public String getBuchungstext() {
        return buchungstext;
    }

    public void setBuchungstext(String buchungstext) {
        this.buchungstext = buchungstext;
    }

    public LocalDate getBuchungsdatum() {
        return buchungsdatum;
    }

    public void setBuchungsdatum(LocalDate buchungsdatum) {
        this.buchungsdatum = buchungsdatum;
    }

    public Integer getLaufnummer() {
        return laufnummer;
    }

    public void setLaufnummer(Integer laufnummer) {
        this.laufnummer = laufnummer;
    }

    public Float getBetrag() {
        return betrag;
    }

    public void setBetrag(Float betrag) {
        this.betrag = betrag;
    }

    public Long getKontoSoll() {
        return kontoSoll;
    }

    public void setKontoSoll(Long kontoSoll) {
        this.kontoSoll = kontoSoll;
    }

    public Long getKontoHaben() {
        return kontoHaben;
    }

    public void setKontoHaben(Long kontoHaben) {
        this.kontoHaben = kontoHaben;
    }

    public Long getMehrwertsteuercode() {
        return mehrwertsteuercode;
    }

    public void setMehrwertsteuercode(Long mehrwertsteuercode) {
        this.mehrwertsteuercode = mehrwertsteuercode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
