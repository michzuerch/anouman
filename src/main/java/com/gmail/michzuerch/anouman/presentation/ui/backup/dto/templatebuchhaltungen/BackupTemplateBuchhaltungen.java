package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.templatebuchhaltungen;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by michzuerch on 25.07.15.
 */
public class BackupTemplateBuchhaltungen {
    private LocalDateTime backupdatum;
    private Set<BackupTemplateBuchhaltung> buchhaltungen = new HashSet<>();

    public BackupTemplateBuchhaltungen() {
    }

    public LocalDateTime getBackupdatum() {
        return backupdatum;
    }

    public void setBackupdatum(LocalDateTime backupdatum) {
        this.backupdatum = backupdatum;
    }

    public Set<BackupTemplateBuchhaltung> getBuchhaltungen() {
        return buchhaltungen;
    }

    public void setBuchhaltungen(Set<BackupTemplateBuchhaltung> buchhaltungen) {
        this.buchhaltungen = buchhaltungen;
    }

    public void addBuchhaltung(BackupTemplateBuchhaltung buchhaltung) {
        this.buchhaltungen.add(buchhaltung);
    }
}
