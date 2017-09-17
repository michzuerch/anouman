package ch.internettechnik.anouman.presentation.reports.rechnung;

/**
 * Created by michzuerch on 31.07.15.
 */
public class Rechnungsposition {
    private String bezeichnung;
    private String bezeichnungLang;
    private String mengeneinheit;
    private Double stueckpreis;
    private Double anzahl;
    private Double positionstotal;

    public Rechnungsposition(String bezeichnung, String bezeichnungLang, String mengeneinheit, Double stueckpreis, Double anzahl, Double positionstotal) {
        this.bezeichnung = bezeichnung;
        this.bezeichnungLang = bezeichnungLang;
        this.mengeneinheit = mengeneinheit;
        this.stueckpreis = stueckpreis;
        this.anzahl = anzahl;
        this.positionstotal = positionstotal;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnungLang() {
        return bezeichnungLang;
    }

    public void setBezeichnungLang(String bezeichnungLang) {
        this.bezeichnungLang = bezeichnungLang;
    }

    public String getMengeneinheit() {
        return mengeneinheit;
    }

    public void setMengeneinheit(String mengeneinheit) {
        this.mengeneinheit = mengeneinheit;
    }

    public Double getStueckpreis() {
        return stueckpreis;
    }

    public void setStueckpreis(Double stueckpreis) {
        this.stueckpreis = stueckpreis;
    }

    public Double getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(Double anzahl) {
        this.anzahl = anzahl;
    }

    public Double getPositionstotal() {
        return positionstotal;
    }

    public void setPositionstotal(Double positionstotal) {
        this.positionstotal = positionstotal;
    }
}
