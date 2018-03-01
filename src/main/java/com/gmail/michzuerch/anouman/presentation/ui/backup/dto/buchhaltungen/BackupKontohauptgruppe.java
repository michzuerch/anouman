package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.buchhaltungen;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by michzuerch on 16.11.15.
 */
public class BackupKontohauptgruppe {
    private String bezeichnung;
    private String kontonummer;
    private Set<BackupKontogruppe> backupKontogruppes = new HashSet<>();

    public BackupKontohauptgruppe() {
    }

    public BackupKontohauptgruppe(String bezeichnung, String kontonummer) {
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

    public Set<BackupKontogruppe> getBackupKontogruppes() {
        return backupKontogruppes;
    }

    public void setBackupKontogruppes(Set<BackupKontogruppe> backupKontogruppes) {
        this.backupKontogruppes = backupKontogruppes;
    }
}
