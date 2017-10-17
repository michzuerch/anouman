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
public class BackupTemplateKontoart {
    @XmlAttribute
    private String bezeichnung;
    @XmlAttribute
    private String kontonummer;
    @XmlElement(name = "sammelkonto")
    private Set<BackupTemplateSammelkonto> backupTemplateSammelkontos = new HashSet<>();

    public BackupTemplateKontoart() {
    }

    public BackupTemplateKontoart(String bezeichnung, String kontonummer) {
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

    public Set<BackupTemplateSammelkonto> getBackupTemplateSammelkontos() {
        return backupTemplateSammelkontos;
    }

    public void setBackupTemplateSammelkontos(Set<BackupTemplateSammelkonto> backupTemplateSammelkontos) {
        this.backupTemplateSammelkontos = backupTemplateSammelkontos;
    }
}
