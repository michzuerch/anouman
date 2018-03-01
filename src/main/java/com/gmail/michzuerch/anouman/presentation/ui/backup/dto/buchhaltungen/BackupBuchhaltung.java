package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.buchhaltungen;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by michzuerch on 15.11.15.
 */
public class BackupBuchhaltung {
    private String bezeichnung;
    private int jahr;

    private Set<BackupKontoklasse> kontoklasses = new HashSet<>();

    public BackupBuchhaltung() {
    }

    public BackupBuchhaltung(String bezeichnung, int jahr) {
        this.bezeichnung = bezeichnung;
        this.jahr = jahr;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public int getJahr() {
        return jahr;
    }

    public void setJahr(int jahr) {
        this.jahr = jahr;
    }

    public Set<BackupKontoklasse> getKontoklasses() {
        return kontoklasses;
    }

    public void setKontoklasses(Set<BackupKontoklasse> kontoklasses) {
        this.kontoklasses = kontoklasses;
    }

}
