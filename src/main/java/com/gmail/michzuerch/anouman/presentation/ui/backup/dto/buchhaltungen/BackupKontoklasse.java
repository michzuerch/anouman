package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.buchhaltungen;

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
public class BackupKontoklasse {
    @XmlAttribute
    private String bezeichnung;
    @XmlAttribute
    private String kontonummer;
    @XmlElement(name = "kontohauptgruppe")
    private Set<BackupKontohauptgruppe> backupKontohauptgruppes = new HashSet<>();

    public BackupKontoklasse(String bezeichnung, String kontonummer) {
        this.bezeichnung = bezeichnung;
        this.kontonummer = kontonummer;
    }

    public BackupKontoklasse() {
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

    public Set<BackupKontohauptgruppe> getBackupKontohauptgruppes() {
        return backupKontohauptgruppes;
    }

    public void setBackupKontohauptgruppes(Set<BackupKontohauptgruppe> backupKontohauptgruppes) {
        this.backupKontohauptgruppes = backupKontohauptgruppes;
    }
}
