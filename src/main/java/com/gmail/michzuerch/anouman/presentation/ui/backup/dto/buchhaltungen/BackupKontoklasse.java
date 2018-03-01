package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.buchhaltungen;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by michzuerch on 16.11.15.
 */
public class BackupKontoklasse {
    private String bezeichnung;
    private String kontonummer;
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
