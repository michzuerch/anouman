package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.artikel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BackupArtikelkategories {
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime backupdatum;

    private List<BackupArtikelkategorie> backupArtikelkategories = new ArrayList<>();

    public List<BackupArtikelkategorie> getBackupArtikelkategories() {
        return backupArtikelkategories;
    }

    public void setBackupArtikelkategories(List<BackupArtikelkategorie> backupArtikelkategories) {
        this.backupArtikelkategories = backupArtikelkategories;
    }

    public LocalDateTime getBackupdatum() {
        return backupdatum;
    }

    public void setBackupdatum(LocalDateTime backupdatum) {
        this.backupdatum = backupdatum;
    }

    @Override
    public String toString() {
        return "BackupArtikelkategories{" +
                "backupArtikelkategories=" + backupArtikelkategories +
                '}';
    }
}
