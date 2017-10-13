package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: michzuerch
 * Date: 31.08.14
 * Time: 17:07
 * To change this template use File | Settings | File Templates.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Rechnung.findAll", query = "SELECT r FROM Rechnung r"),
        @NamedQuery(name = "Rechnung.findById", query = "SELECT r FROM Rechnung r where r.id = :id"),
        @NamedQuery(name = "Rechnung.findByDatum", query = "SELECT r FROM Rechnung r where r.rechnungsdatum = :datum"),
        @NamedQuery(name = "Rechnung.findByBezeichnungLike", query = "SELECT r FROM Rechnung r where r.bezeichnung LIKE :bezeichnung")
})
public class Rechnung extends AbstractEntity {
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date rechnungsdatum;

    @Column
    @NotNull
    @Size(min = 3, max = 50)
    private String bezeichnung;

    @Column
    @NotNull
    @Digits(integer = 2, fraction = 0, message = "Ungültiges Zahlenformat")
    @DecimalMin(value = "1", message = "Minimal 1 Tag")
    @DecimalMax(value = "365", message = "Maximal 365 Tage")
    private int faelligInTagen;

    @Column
    private boolean bezahlt;

    @Column
    private boolean verschickt;

    @ManyToOne
    private Adresse adresse;

    @OneToMany(mappedBy = "rechnung", cascade = CascadeType.ALL)
    private List<Rechnungsposition> rechnungspositionen = new ArrayList<>();

    @OneToMany(mappedBy = "rechnung", cascade = CascadeType.ALL)
    private List<Aufwand> aufwands = new ArrayList<>();

    public Rechnung() {
    }

    public Rechnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Date getRechnungsdatum() {
        return rechnungsdatum;
    }

    public void setRechnungsdatum(Date rechnungsdatum) {
        this.rechnungsdatum = rechnungsdatum;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public int getFaelligInTagen() {
        return faelligInTagen;
    }

    public void setFaelligInTagen(int faelligInTagen) {
        this.faelligInTagen = faelligInTagen;
    }

    public boolean isBezahlt() {
        return bezahlt;
    }

    public void setBezahlt(boolean bezahlt) {
        this.bezahlt = bezahlt;
    }

    public boolean isVerschickt() {
        return verschickt;
    }

    public void setVerschickt(boolean verschickt) {
        this.verschickt = verschickt;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public List<Rechnungsposition> getRechnungspositionen() {
        return rechnungspositionen;
    }

    public void setRechnungspositionen(List<Rechnungsposition> rechnungspositionen) {
        this.rechnungspositionen = rechnungspositionen;
    }

    public List<Aufwand> getAufwands() {
        return aufwands;
    }

    public void setAufwands(List<Aufwand> aufwands) {
        this.aufwands = aufwands;
    }

    @Transient
    public Float getZwischentotal() {
        BigDecimal zt = new BigDecimal(0f);
        for (Rechnungsposition p : getRechnungspositionen()) {
            BigDecimal pt = new BigDecimal(p.getPositionstotal());
            zt = zt.add(pt);
        }
        for (Aufwand aw : getAufwands()) {
            BigDecimal pt = new BigDecimal(aw.getPositionstotal());
            zt = zt.add(pt);
        }
        Float result = zt.floatValue();
        return result;
    }

    @Transient
    public Float getMehrwertsteuer() {
        BigDecimal zt = new BigDecimal(getZwischentotal());
        BigDecimal mwst = zt.multiply(BigDecimal.valueOf(0.08));
        return mwst.floatValue();
    }

    @Transient
    public Float getRechnungstotal() {
        BigDecimal zt = new BigDecimal(getZwischentotal());
        zt = zt.add(new BigDecimal(getMehrwertsteuer()));
        return zt.floatValue();
    }

    @Transient
    public int getAnzahlRechnungspositionenUndAufwands() {
        int t = getRechnungspositionen().size() + getAufwands().size();
        return t;
    }

    @Transient
    public int getAnzahlRechnungspositionen() {
        int t = getRechnungspositionen().size();
        return t;
    }

    @Transient
    public int getAnzahlAufwands() {
        int t = getAufwands().size();
        return t;
    }

    @Transient
    public Date getFaelligkeitsdatum() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getRechnungsdatum());
        cal.add(Calendar.DATE, getFaelligInTagen());
        return cal.getTime();
    }

}
