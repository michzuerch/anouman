package com.gmail.michzuerch.anouman.presentation.ui.backup.xml.buchhaltungen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by michzuerch on 16.11.15.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class BackupMehrwertsteuercode {
    @XmlAttribute
    private Long id;

    @XmlElement
    private String bezeichnung;

    @XmlElement
    private String code;

    @XmlElement
    private Float prozent;

    @XmlElement
    private boolean verkauf;

    @XmlElement
    private Long konto;

    public BackupMehrwertsteuercode(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public BackupMehrwertsteuercode() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Float getProzent() {
        return prozent;
    }

    public void setProzent(Float prozent) {
        this.prozent = prozent;
    }

    public boolean isVerkauf() {
        return verkauf;
    }

    public void setVerkauf(boolean verkauf) {
        this.verkauf = verkauf;
    }

    public Long getKonto() {
        return konto;
    }

    public void setKonto(Long konto) {
        this.konto = konto;
    }
}
