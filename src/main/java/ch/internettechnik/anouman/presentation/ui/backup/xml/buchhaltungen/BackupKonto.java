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
    private String bemerkung;
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
    @XmlElement(name = "mehrwersteuercode")
    private Set<BackupMehrwertsteuercode> mehrwertsteuercodes = new HashSet<>();

    public BackupKonto() {
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBemerkung() {
        return bemerkung;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
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

    public Set<BackupBuchung> getBuchungen() {
        return buchungen;
    }

    public void setBuchungen(Set<BackupBuchung> buchungen) {
        this.buchungen = buchungen;
    }

    public Set<BackupMehrwertsteuercode> getMehrwertsteuercodes() {
        return mehrwertsteuercodes;
    }

    public void setMehrwertsteuercodes(Set<BackupMehrwertsteuercode> mehrwertsteuercodes) {
        this.mehrwertsteuercodes = mehrwertsteuercodes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
