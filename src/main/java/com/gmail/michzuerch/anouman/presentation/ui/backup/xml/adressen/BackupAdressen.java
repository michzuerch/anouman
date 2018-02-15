package com.gmail.michzuerch.anouman.presentation.ui.backup.xml.adressen;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by michzuerch on 25.07.15.
 */
@XmlRootElement(name = "adressen", namespace = "http://www.internettechnik.ch/Anouman")
@XmlAccessorType(XmlAccessType.NONE)
public class BackupAdressen {
    @XmlAttribute
    private Date backupdatum;

    @XmlElement(name = "adresse")
    private List<BackupAdresse> adressen = new ArrayList<>();

    public List<BackupAdresse> getAdressen() {
        return adressen;
    }

    public void setAdressen(List<BackupAdresse> adressen) {
        this.adressen = adressen;
    }

    public Date getBackupdatum() {
        return backupdatum;
    }

    public void setBackupdatum(Date backupdatum) {
        this.backupdatum = backupdatum;
    }
}
