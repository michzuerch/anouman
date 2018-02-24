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
public class BackupTemplateKontohauptgruppe {
    @XmlAttribute
    private String bezeichnung;
    @XmlAttribute
    private String kontonummer;
    @XmlElement(name = "kontogruppe")
    private Set<BackupTemplateKontogruppe> backupTemplateKontogruppes = new HashSet<>();

    public BackupTemplateKontohauptgruppe() {
    }

    public BackupTemplateKontohauptgruppe(String bezeichnung, String kontonummer) {
        this.bezeichnung = bezeichnung;
        this.kontonummer = kontonummer;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(String kontonummer) {
        this.kontonummer = kontonummer;
    }

    public Set<BackupTemplateKontogruppe> getBackupTemplateKontogruppes() {
        return backupTemplateKontogruppes;
    }

    public void setBackupTemplateKontogruppes(Set<BackupTemplateKontogruppe> backupTemplateKontogruppes) {
        this.backupTemplateKontogruppes = backupTemplateKontogruppes;
    }

}
