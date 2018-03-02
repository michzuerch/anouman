package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.adressen;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BackupRechnung {
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate rechnungsdatum;
    private String bezeichnung;
    private int faelligInTagen;
    private boolean bezahlt;
    private boolean verschickt;
    private List<BackupRechnungsposition> rechnungspositions = new ArrayList<>();
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

    @Override
    public String toString() {
        return "BackupRechnung{" +
                "rechnungsdatum=" + rechnungsdatum +
                ", bezeichnung='" + bezeichnung + '\'' +
                ", faelligInTagen=" + faelligInTagen +
                ", bezahlt=" + bezahlt +
                ", verschickt=" + verschickt +
                ", rechnungspositions=" + rechnungspositions +
                ", aufwands=" + aufwands +
                '}';
    }
}
