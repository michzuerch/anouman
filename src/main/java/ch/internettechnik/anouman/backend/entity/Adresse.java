package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

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

public class Adresse extends AbstractEntity {
    @Column
    private String firma;

    @Column
    private String anrede;

    @Column
    private String vorname;

    @Column
    @NotNull
    @Pattern(regexp = "[a-z-A-Z]*", message = "Nachname enthält ungültige Zeichen")
    private String nachname;

    @Column
    private String strasse;

    @Column
    @NotNull
    @Size(min = 4, max = 5)
    @Pattern(regexp = "[0-9]*", message = "Postleitzahl enthält ungültige Zeichen")
    private String postleitzahl;

    @Column
    @NotNull
    @Size(min = 3)
    private String ort;

    @Column
    @NotNull
    @Digits(integer = 8, fraction = 2, message = "Muss ein gültiger Betrag sein")
    @DecimalMin(value = "0.01", message = "Minimaler Betrag ist 0.01")
    @DecimalMax(value = "500", message = "Maximaler Betrag ist 500")
    private Double stundensatz;

    @OneToMany(mappedBy = "adresse", cascade = CascadeType.ALL)
    private List<Rechnung> rechnungen = new ArrayList<>();

    public Adresse(String firma, String anrede, String vorname, String nachname, String strasse, String postleitzahl, String ort, Double stundensatz) {
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

    public List<Rechnung> getRechnungen() {
        return rechnungen;
    }

    public void setRechnungen(List<Rechnung> rechnungen) {
        this.rechnungen = rechnungen;
    }

    public Double getStundensatz() {
        return stundensatz;
    }

    public void setStundensatz(Double stundensatz) {
        this.stundensatz = stundensatz;
    }
}
