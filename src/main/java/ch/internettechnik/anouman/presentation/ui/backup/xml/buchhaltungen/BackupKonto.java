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
public class BackupKonto {
    @XmlAttribute
    private String bezeichnung;
    @XmlAttribute
    private String kontonummer;
    @XmlElement
    private String showKontonummer;
    @XmlElement
    private Double anfangsbestand;
    @XmlAttribute
    private Long id;
    @XmlElement(name = "buchung")
    private Set<BackupBuchung> buchungen = new HashSet<>();

    public BackupKonto() {
    }

    public BackupKonto(Long id, String bezeichnung, String kontonummer, String showKontonummer, Double anfangsbestand) {
        this.id = id;
        this.bezeichnung = bezeichnung;
        this.kontonummer = kontonummer;
        this.showKontonummer = showKontonummer;
        this.anfangsbestand = anfangsbestand;
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

    public Double getAnfangsbestand() {
        return anfangsbestand;
    }

    public void setAnfangsbestand(Double anfangsbestand) {
        this.anfangsbestand = anfangsbestand;
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
