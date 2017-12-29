package ch.internettechnik.anouman.presentation.ui.backup.xml.templatebuchhaltungen;

import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by michzuerch on 25.07.15.
 */
@XmlRootElement(name = "buchhaltungen", namespace = "http://www.internettechnik.ch/Anouman")
@XmlAccessorType(XmlAccessType.NONE)
public class BackupTemplateBuchhaltungen {
    @XmlAttribute
    private Date datum;
    @XmlElement(name = "buchhaltung")
    private Set<BackupTemplateBuchhaltung> buchhaltungen = new HashSet<>();

    public BackupTemplateBuchhaltungen() {
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
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
