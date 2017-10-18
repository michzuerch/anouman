package ch.internettechnik.anouman.presentation.ui.backup.xml.templatebuchhaltungen;

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
public class BackupTemplateKontoklasse {
    @XmlAttribute
    private String bezeichnung;
    @XmlAttribute
    private String kontonummer;
    @XmlElement(name = "kontohauptgruppe")
    private Set<BackupTemplateKontohauptgruppe> kontohauptgruppen = new HashSet<>();

    public BackupTemplateKontoklasse(String bezeichnung, String kontonummer) {
        this.bezeichnung = bezeichnung;
        this.kontonummer = kontonummer;
    }

    public BackupTemplateKontoklasse() {
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

    public Set<BackupTemplateKontohauptgruppe> getKontohauptgruppen() {
        return kontohauptgruppen;
    }

    public void setKontohauptgruppen(Set<BackupTemplateKontohauptgruppe> kontohauptgruppen) {
        this.kontohauptgruppen = kontohauptgruppen;
    }
}
