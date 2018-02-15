package com.gmail.michzuerch.anouman.presentation.ui.backup.xml.templatebuchhaltungen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by michzuerch on 15.11.15.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class BackupTemplateBuchhaltung {
    @XmlAttribute
    private String bezeichnung;

    @XmlElement(name = "kontoklasse")
    private Set<BackupTemplateKontoklasse> kontoklasses = new HashSet<>();

    @XmlElement(name = "mehrwertsteuercode")
    private Set<BackupTemplateMehrwertsteuercode> mehrwertsteuercodes = new HashSet<>();

    public BackupTemplateBuchhaltung() {
    }

    public BackupTemplateBuchhaltung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Set<BackupTemplateKontoklasse> getKontoklasses() {
        return kontoklasses;
    }

    public void setKontoklasses(Set<BackupTemplateKontoklasse> kontoklasses) {
        this.kontoklasses = kontoklasses;
    }

    public Set<BackupTemplateMehrwertsteuercode> getMehrwertsteuercodes() {
        return mehrwertsteuercodes;
    }

    public void setMehrwertsteuercodes(Set<BackupTemplateMehrwertsteuercode> mehrwertsteuercodes) {
        this.mehrwertsteuercodes = mehrwertsteuercodes;
    }
}
