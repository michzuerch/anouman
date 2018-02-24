package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.templatebuchhaltungen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by michzuerch on 16.11.15.
 */

@XmlAccessorType(XmlAccessType.NONE)
public class BackupTemplateKonto {
    @XmlAttribute
    private String bezeichnung;
    @XmlAttribute
    private String bemerkung;
    @XmlAttribute
    private String kontonummer;
    @XmlElement
    private String showKontonummer;
    @XmlElement(name = "templatemehrwertsteuercode")
    private Set<BackupTemplateMehrwertsteuercode> backupTemplateMehrwertsteuercodes = new HashSet<>();


    private Long id;

    public BackupTemplateKonto() {
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

    public String getShowKontonummer() {
        return showKontonummer;
    }

    public void setShowKontonummer(String showKontonummer) {
        this.showKontonummer = showKontonummer;
    }

    public Set<BackupTemplateMehrwertsteuercode> getBackupTemplateMehrwertsteuercodes() {
        return backupTemplateMehrwertsteuercodes;
    }

    public void setBackupTemplateMehrwertsteuercodes(Set<BackupTemplateMehrwertsteuercode> backupTemplateMehrwertsteuercodes) {
        this.backupTemplateMehrwertsteuercodes = backupTemplateMehrwertsteuercodes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
