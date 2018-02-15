package com.gmail.michzuerch.anouman.presentation.ui.backup.xml.adressen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
public class BackupRechnung {

    @XmlElement
    private LocalDate rechnungsdatum;

    @XmlElement
    private String bezeichnung;

    @XmlElement
    private int faelligInTagen;

    @XmlElement
    private boolean bezahlt;

    @XmlElement
    private boolean verschickt;


    @XmlElement(name = "rechnungsposition")
    private List<BackupRechnungsposition> rechnungspositions = new ArrayList<>();

    @XmlElement(name = "aufwand")
    private List<BackupAufwand> aufwands = new ArrayList<>();

    public LocalDate getRechnungsdatum() {
        return rechnungsdatum;
    }

    public void setRechnungsdatum(LocalDate rechnungsdatum) {
        this.rechnungsdatum = rechnungsdatum;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public int getFaelligInTagen() {
        return faelligInTagen;
    }

    public void setFaelligInTagen(int faelligInTagen) {
        this.faelligInTagen = faelligInTagen;
    }

    public boolean isBezahlt() {
        return bezahlt;
    }

    public void setBezahlt(boolean bezahlt) {
        this.bezahlt = bezahlt;
    }

    public boolean isVerschickt() {
        return verschickt;
    }

    public void setVerschickt(boolean verschickt) {
        this.verschickt = verschickt;
    }

    public List<BackupRechnungsposition> getRechnungspositions() {
        return rechnungspositions;
    }

    public void setRechnungspositions(List<BackupRechnungsposition> rechnungspositions) {
        this.rechnungspositions = rechnungspositions;
    }

    public List<BackupAufwand> getAufwands() {
        return aufwands;
    }

    public void setAufwands(List<BackupAufwand> aufwands) {
        this.aufwands = aufwands;
    }
}
