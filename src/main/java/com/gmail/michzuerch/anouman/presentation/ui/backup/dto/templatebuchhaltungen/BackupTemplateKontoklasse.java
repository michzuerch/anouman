package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.templatebuchhaltungen;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by michzuerch on 16.11.15.
 */
public class BackupTemplateKontoklasse {
    private String bezeichnung;
    private String kontonummer;
    private Long id;
    private Set<BackupTemplateKontohauptgruppe> kontohauptgruppen = new HashSet<>();

    public BackupTemplateKontoklasse(String bezeichnung, String kontonummer) {
        this.bezeichnung = bezeichnung;
        this.kontonummer = kontonummer;
    }

    public BackupTemplateKontoklasse() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<BackupTemplateKontohauptgruppe> getKontohauptgruppen() {
        return kontohauptgruppen;
    }

    public void setKontohauptgruppen(Set<BackupTemplateKontohauptgruppe> kontohauptgruppen) {
        this.kontohauptgruppen = kontohauptgruppen;
    }
}
