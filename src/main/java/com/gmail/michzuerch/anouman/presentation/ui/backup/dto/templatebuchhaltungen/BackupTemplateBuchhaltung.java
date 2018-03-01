package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.templatebuchhaltungen;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by michzuerch on 15.11.15.
 */
public class BackupTemplateBuchhaltung {
    private String bezeichnung;
    private Set<BackupTemplateKontoklasse> kontoklasses = new HashSet<>();
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
