package ch.internettechnik.anouman.presentation.ui.backup.xml.buchhaltungen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by michzuerch on 16.11.15.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class BackupKontoklasse {
    @XmlAttribute
    private String bezeichnung;
    @XmlAttribute
    private String kontonummer;
    @XmlElement(name = "kontogruppe")
    private Set<BackupKontogruppe> kontogruppen = new HashSet<>();

    public BackupKontoklasse(String bezeichnung, String kontonummer) {
        this.bezeichnung = bezeichnung;
        this.kontonummer = kontonummer;
    }

    public BackupKontoklasse() {
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(String kontonummer) {
        this.kontonummer = kontonummer;
    }

    public Set<BackupKontogruppe> getKontogruppen() {
        return kontogruppen;
    }

    public void setKontogruppen(Set<BackupKontogruppe> kontogruppen) {
        this.kontogruppen = kontogruppen;
    }

    public void addKontogruppe(BackupKontogruppe backupKontogruppe) {
        this.kontogruppen.add(backupKontogruppe);
    }
}
