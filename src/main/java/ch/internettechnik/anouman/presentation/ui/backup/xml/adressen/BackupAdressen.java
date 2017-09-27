package ch.internettechnik.anouman.presentation.ui.backup.xml.adressen;

import ch.internettechnik.anouman.backend.entity.Adresse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by michzuerch on 25.07.15.
 */
@XmlRootElement(name = "adressen", namespace = "http://www.internettechnik.ch/Anouman")
@XmlAccessorType(XmlAccessType.NONE)
public class BackupAdressen {
    @XmlElement(name = "adresse")
    private List<Adresse> adressen = null;

    public List<Adresse> getAdressen() {
        return adressen;
    }

    public void setAdressen(List<Adresse> adressen) {
        this.adressen = adressen;
    }
}
