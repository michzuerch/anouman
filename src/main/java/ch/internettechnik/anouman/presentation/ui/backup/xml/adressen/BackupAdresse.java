package ch.internettechnik.anouman.presentation.ui.backup.xml.adressen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
public class BackupAdresse {
    @XmlElement
    private String firma;

    @XmlElement
    private String anrede;

    @XmlElement
    private String vorname;

    @XmlElement
    private String nachname;

    @XmlElement
    private String strasse;

    @XmlElement
    private String ort;

    @XmlElement
    private String postleitzahl;

    @XmlElement
    private Double stundensatz;

    @XmlElement(name = "rechnung")
    private List<BackupRechnung> rechnungen = new ArrayList<>();


    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(String postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public Double getStundensatz() {
        return stundensatz;
    }

    public void setStundensatz(Double stundensatz) {
        this.stundensatz = stundensatz;
    }

    public List<BackupRechnung> getRechnungen() {
        return rechnungen;
    }

    public void setRechnungen(List<BackupRechnung> rechnungen) {
        this.rechnungen = rechnungen;
    }
}
