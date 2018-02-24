package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.buchhaltungen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

/**
 * Created by michzuerch on 16.11.15.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class BackupBuchung {
    @XmlElement
    private String buchungstext;

    @XmlElement
    private Date buchungsdatum;

    @XmlElement
    private Integer laufnummer;

    @XmlElement
    private Float betrag;

    @XmlElement
    private Long kontoSoll;

    @XmlElement
    private Long kontoHaben;

    @XmlAttribute
    private Long id;

    @XmlElement
    private Long mehrwertsteuercode;

    public BackupBuchung() {
    }

    public String getBuchungstext() {
        return buchungstext;
    }

    public void setBuchungstext(String buchungstext) {
        this.buchungstext = buchungstext;
    }

    public Date getBuchungsdatum() {
        return buchungsdatum;
    }

    public void setBuchungsdatum(Date buchungsdatum) {
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
