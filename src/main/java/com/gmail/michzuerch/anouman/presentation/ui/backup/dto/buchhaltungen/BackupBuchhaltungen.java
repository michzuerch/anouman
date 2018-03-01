package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.buchhaltungen;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by michzuerch on 25.07.15.
 */
public class BackupBuchhaltungen {
    private LocalDateTime backupdatum;

    private Set<BackupBuchhaltung> buchhaltungen = new HashSet<>();

    public BackupBuchhaltungen() {
    }

    public Set<BackupBuchhaltung> getBuchhaltungen() {
        return buchhaltungen;
    }

    public void setBuchhaltungen(Set<BackupBuchhaltung> buchhaltungen) {
        this.buchhaltungen = buchhaltungen;
    }

    public LocalDateTime getBackupdatum() {
        return backupdatum;
    }

    public void setBackupdatum(LocalDateTime backupdatum) {
        this.backupdatum = backupdatum;
    }

    public void addBuchhaltung(BackupBuchhaltung buchhaltung) {
        this.buchhaltungen.add(buchhaltung);
    }
}
