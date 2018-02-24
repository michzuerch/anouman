package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.templatebuchhaltungen;

import javax.xml.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by michzuerch on 25.07.15.
 */
@XmlRootElement(name = "buchhaltungen", namespace = "http://www.internettechnik.ch/Anouman")
@XmlAccessorType(XmlAccessType.NONE)
public class BackupTemplateBuchhaltungen {
    @XmlAttribute
    private LocalDateTime backupdatum;
    @XmlElement(name = "buchhaltung")
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
