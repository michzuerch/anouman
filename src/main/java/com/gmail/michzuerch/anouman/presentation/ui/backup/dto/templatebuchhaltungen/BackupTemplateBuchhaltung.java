package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.templatebuchhaltungen;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by michzuerch on 15.11.15.
 */
public class BackupTemplateBuchhaltung {
    private String bezeichnung;
    private Long id;
    private Set<BackupTemplateKontoklasse> kontoklasses = new HashSet<>();

    public BackupTemplateBuchhaltung() {
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<BackupTemplateKontoklasse> getKontoklasses() {
        return kontoklasses;
    }

    public void setKontoklasses(Set<BackupTemplateKontoklasse> kontoklasses) {
        this.kontoklasses = kontoklasses;
    }

}
