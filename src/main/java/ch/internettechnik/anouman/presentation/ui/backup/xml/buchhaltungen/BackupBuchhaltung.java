package ch.internettechnik.anouman.presentation.ui.backup.xml.buchhaltungen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by michzuerch on 15.11.15.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class BackupBuchhaltung {
    @XmlAttribute
    private String bezeichnung;
    @XmlAttribute
    private int jahr;

    @XmlElement(name = "buchung")
    private Set<BackupBuchung> buchungen = new HashSet<>();

    @XmlElement(name = "kontoklasse")
    private Set<BackupKontoklasse> kontoklasses = new HashSet<>();

    @XmlElement(name = "mehrwertsteuercode")
    private Set<BackupMehrwertsteuercode> mehrwertsteuercodes = new HashSet<>();

    public BackupBuchhaltung() {
    }

    public BackupBuchhaltung(String bezeichnung, int jahr) {
        this.bezeichnung = bezeichnung;
        this.jahr = jahr;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public int getJahr() {
        return jahr;
    }

    public void setJahr(int jahr) {
        this.jahr = jahr;
    }

    public Set<BackupKontoklasse> getKontoklasses() {
        return kontoklasses;
    }

    public void setKontoklasses(Set<BackupKontoklasse> kontoklasses) {
        this.kontoklasses = kontoklasses;
    }

    public Set<BackupMehrwertsteuercode> getMehrwertsteuercodes() {
        return mehrwertsteuercodes;
    }

    public void setMehrwertsteuercodes(Set<BackupMehrwertsteuercode> mehrwertsteuercodes) {
        this.mehrwertsteuercodes = mehrwertsteuercodes;
    }

    public Set<BackupBuchung> getBuchungen() {
        return buchungen;
    }

    public void setBuchungen(Set<BackupBuchung> buchungen) {
        this.buchungen = buchungen;
    }
}
