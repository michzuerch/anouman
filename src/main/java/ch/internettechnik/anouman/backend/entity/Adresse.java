package ch.internettechnik.anouman.backend.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by michzuerch on 03.03.15.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Adresse.findAll", query = "SELECT a FROM Adresse a"),
        @NamedQuery(name = "Adresse.findById", query = "SELECT a FROM Adresse a where a.id = :id"),
        @NamedQuery(name = "Adresse.findByFirma", query = "SELECT a FROM Adresse a where a.firma = :firma"),
        @NamedQuery(name = "Adresse.findByNachname", query = "SELECT a FROM Adresse a where a.nachname = :nachname"),
        @NamedQuery(name = "Adresse.findByOrt", query = "SELECT a FROM Adresse a where a.ort = :ort"),
        @NamedQuery(name = "Adresse.findByFirmaNachname", query = "SELECT a FROM Adresse a where a.firma = :firma and a.nachname = :nachname"),
        @NamedQuery(name = "Adresse.findByFirmaOrt", query = "SELECT a FROM Adresse a where a.firma = :firma and a.ort = :ort"),
        @NamedQuery(name = "Adresse.findByFirmaNachnameOrt", query = "SELECT a FROM Adresse a where a.firma = :firma and a.nachname = :nachname and a.ort = :ort"),
        @NamedQuery(name = "Adresse.findByNachnameOrt", query = "SELECT a FROM Adresse a where a.nachname = :nachname and a.ort = :ort")
})

//@Email(message = "Email Address is not a valid format")

@XmlRootElement(name = "adresse")
@XmlAccessorType(XmlAccessType.NONE)
public class Adresse extends AbstractEntity {
    @Column
    @XmlElement
    private String firma;

    @Column
    @XmlElement
    private String anrede;

    @Column
    @XmlElement
    private String vorname;

    @Column
    @NotNull
    @NotBlank(message = "Nachname muss eingegeben werden.")
    @Pattern(regexp = "[a-z-A-Z]*", message = "Nachname enthält ungültige Zeichen")
    @XmlElement
    private String nachname;

    @Column
    @XmlElement
    private String strasse;

    @Column
    @NotNull
    @Size(min = 4, max = 5)
    @NotBlank(message = "Postleitzahl muss eingegeben werden.")
    @Pattern(regexp = "[0-9]*", message = "Postleitzahl enthält ungültige Zeichen")
    @XmlElement
    private String postleitzahl;

    @Column
    @NotNull
    @NotBlank(message = "Ort muss eingegeben werden.")
    @Size(min = 3)
    @XmlElement
    private String ort;

    @Column
    @NotNull
    @Digits(integer = 8, fraction = 2, message = "Muss ein gültiger Betrag sein")
    @Range(min = 10, max = 500)
    @XmlElement
    private Double stundensatz;

    @OneToMany(mappedBy = "adresse")
    @XmlElement(name = "rechnungen")
    private Set<Rechnung> rechnungen = new HashSet<Rechnung>();

    public Adresse(String firma, String anrede, String vorname, String nachname, String strasse, String postleitzahl, @NotBlank(message = "Ort muss eingegeben werden.") String ort, Double stundensatz) {
        this.firma = firma;
        this.anrede = anrede;
        this.vorname = vorname;
        this.nachname = nachname;
        this.strasse = strasse;
        this.postleitzahl = postleitzahl;
        this.ort = ort;
        this.stundensatz = stundensatz;
    }

    public Adresse() {
    }

    @Transient
    public Integer getAnzahlRechnungen() {
        return new Integer(getRechnungen().size());
    }

    @Transient
    public Double getOffenePostenTotal() {
        double total = 0;
        for (Rechnung rechnung : getRechnungen()) {
            if (rechnung.isBezahlt() == false) total = total + rechnung.getRechnungstotal();
        }
        return new Double(total);
    }

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

    public String getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(String postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public Set<Rechnung> getRechnungen() {
        return rechnungen;
    }

    public void setRechnungen(Set<Rechnung> rechnungen) {
        this.rechnungen = rechnungen;
    }

    public Double getStundensatz() {
        return stundensatz;
    }

    public void setStundensatz(Double stundensatz) {
        this.stundensatz = stundensatz;
    }
}
