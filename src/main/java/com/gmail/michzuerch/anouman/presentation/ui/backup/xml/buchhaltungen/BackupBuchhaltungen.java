package com.gmail.michzuerch.anouman.presentation.ui.backup.xml.buchhaltungen;

import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by michzuerch on 25.07.15.
 */
@XmlRootElement(name = "buchhaltungen", namespace = "http://www.internettechnik.ch/Anouman")
@XmlAccessorType(XmlAccessType.FIELD)
public class BackupBuchhaltungen {
    @XmlAttribute
    private Date datum;

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

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public void addBuchhaltung(BackupBuchhaltung buchhaltung) {
        this.buchhaltungen.add(buchhaltung);
    }
}
