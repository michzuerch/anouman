package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by michzuerch on 07.08.15.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Buchung.findAll", query = "SELECT b FROM Buchung b"),
        @NamedQuery(name = "Buchung.findById", query = "SELECT b FROM Buchung b where b.id = :id")
})
public class Buchung extends AbstractEntity {
    @OneToMany(mappedBy = "buchung", cascade = CascadeType.ALL)
    private List<Unterbuchung> unterbuchungs = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "MWSTCODE_ID", nullable = false)
    private Mehrwertsteuercode mehrwertsteuercode;

    @Lob
    @Basic
    private byte[] belegkopie;

    public Mehrwertsteuercode getMehrwertsteuercode() {
        return mehrwertsteuercode;
    }

    public void setMehrwertsteuercode(Mehrwertsteuercode mehrwertsteuercode) {
        this.mehrwertsteuercode = mehrwertsteuercode;
    }

    public byte[] getBelegkopie() {
        return belegkopie;
    }

    public void setBelegkopie(byte[] belegkopie) {
        this.belegkopie = belegkopie;
    }

    public List<Unterbuchung> getUnterbuchungs() {
        return unterbuchungs;
    }

    public void setUnterbuchungs(List<Unterbuchung> unterbuchungs) {
        this.unterbuchungs = unterbuchungs;
    }

    @Override
    public String toString() {
        return "Buchung{" +
                "unterbuchungs=" + unterbuchungs +
                ", mehrwertsteuercode=" + mehrwertsteuercode +
                ", belegkopie=" + Arrays.toString(belegkopie) +
                '}';
    }
}
