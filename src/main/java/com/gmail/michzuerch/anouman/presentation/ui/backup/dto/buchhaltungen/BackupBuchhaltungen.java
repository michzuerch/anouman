package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.buchhaltungen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by michzuerch on 25.07.15.
 */
@XmlRootElement(name = "buchhaltungen", namespace = "http://www.internettechnik.ch/Anouman")
@XmlAccessorType(XmlAccessType.FIELD)
public class BackupBuchhaltungen {
    @XmlElement(name = "backupdatum")
    private LocalDateTime backupdatum;

    @XmlElement(name = "buchhaltung")
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
