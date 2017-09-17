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
public class BackupKontoart {
    @XmlAttribute
    private String bezeichnung;
    @XmlAttribute
    private String kontonummer;
    @XmlElement(name = "konto")
    private Set<BackupKonto> konti = new HashSet<>();

    public BackupKontoart() {
    }

    public BackupKontoart(String bezeichnung, String kontonummer) {
        this.bezeichnung = bezeichnung;
        this.kontonummer = kontonummer;
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

    public Set<BackupKonto> getKonti() {
        return konti;
    }

    public void setKonti(Set<BackupKonto> konti) {
        this.konti = konti;
    }

}
