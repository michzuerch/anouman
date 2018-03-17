package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.artikel;

import java.util.ArrayList;
import java.util.List;

public class BackupArtikelkategorie {
    private List<BackupArtikel> backupArtikels = new ArrayList<>();
    private String bezeichnung;

    public List<BackupArtikel> getBackupArtikels() {
        return backupArtikels;
    }

    public void setBackupArtikels(List<BackupArtikel> backupArtikels) {
        this.backupArtikels = backupArtikels;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    @Override
    public String toString() {
        return "BackupArtikelkategorie{" +
                "backupArtikels=" + backupArtikels +
                '}';
    }
}
