package ch.internettechnik.anouman.presentation.ui.backup.xml.templatebuchhaltungen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by michzuerch on 16.11.15.
 */

@XmlAccessorType(XmlAccessType.NONE)
public class BackupTemplateKonto {
    @XmlAttribute
    private String bezeichnung;
    @XmlAttribute
    private String kontonummer;
    @XmlElement
    private String showKontonummer;

    private Long id;

    public BackupTemplateKonto() {
    }

    public BackupTemplateKonto(Long id, String bezeichnung, String kontonummer, String showKontonummer) {
        this.id = id;
        this.bezeichnung = bezeichnung;
        this.kontonummer = kontonummer;
        this.showKontonummer = showKontonummer;
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

    public String getShowKontonummer() {
        return showKontonummer;
    }

    public void setShowKontonummer(String showKontonummer) {
        this.showKontonummer = showKontonummer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
