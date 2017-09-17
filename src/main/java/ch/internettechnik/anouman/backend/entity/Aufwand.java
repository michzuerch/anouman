package ch.internettechnik.anouman.backend.entity;

/**
 * Created by michzuerch on 06.07.15.
 */

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "Aufwand.findAll", query = "SELECT a FROM Aufwand a order by a.start"),
        @NamedQuery(name = "Aufwand.findById", query = "SELECT a FROM Aufwand a where a.id = :id"),
        @NamedQuery(name = "Aufwand.findByTitel", query = "SELECT a FROM Aufwand a where a.titel LIKE :titel")
})
@XmlAccessorType(XmlAccessType.NONE)
public class Aufwand extends AbstractEntity {
    @Column
    @NotNull
    @NotBlank(message = "Titel muss eingegeben werden.")
    @XmlElement
    private String titel;

    @Column
    @XmlElement
    private String bezeichnung;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @XmlElement
    private Date start;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @XmlElement
    private Date ende;

    @ManyToOne
    private ch.internettechnik.anouman.backend.entity.Rechnung rechnung;

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnde() {
        return ende;
    }

    public void setEnde(Date ende) {
        this.ende = ende;
    }

    public Rechnung getRechnung() {
        return rechnung;
    }

    public void setRechnung(Rechnung rechnung) {
        this.rechnung = rechnung;
    }

    @Transient
    public Double getDauerInMinuten() {
        return Double.valueOf(((ende.getTime() - start.getTime()) / 1000) / 60);
    }

    @Transient
    public Double getDauerInStunden() {
        return Double.valueOf((((ende.getTime() - start.getTime()) / 1000) / 60) / 60);
    }

    @Transient
    public Double getPositionstotal() {
        BigDecimal stk = null;
        if (getRechnung() != null) {
            stk = new BigDecimal(getRechnung().getAdresse().getStundensatz());
        } else {
            stk = new BigDecimal(130);
        }
        BigDecimal anz = new BigDecimal(getDauerInStunden());
        BigDecimal total = stk.multiply(anz);
        return total.doubleValue();
    }

    public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
        this.rechnung = (Rechnung) parent;
    }

    @Override
    public String toString() {
        //DateFormat format = SimpleDateFormat.getDateInstance()
        return "Aufwand{" +
                "titel='" + titel + '\'' +
                ", bezeichnung='" + bezeichnung + '\'' +
                ", start=" + new SimpleDateFormat("dd.MM. HH:mm").format(start) +
                ", ende=" + new SimpleDateFormat("dd.MM. HH:mm").format(ende) +
                '}';
    }

    /*
    @PrePersist
    @PreUpdate
    public void pre() {
        System.err.println("Pre:"+ toString());
    }
    */

    /*
    @PostPersist
    @PostUpdate
    public void post() {
        System.err.println("Post:" + toString());
    }
    */
}
