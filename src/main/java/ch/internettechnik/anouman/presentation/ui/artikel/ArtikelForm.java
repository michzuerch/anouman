package ch.internettechnik.anouman.presentation.ui.artikel;

import ch.internettechnik.anouman.backend.entity.Artikel;
import ch.internettechnik.anouman.backend.entity.Artikelkategorie;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelkategorieFacade;
import ch.internettechnik.anouman.presentation.ui.FloatField;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

import javax.inject.Inject;

@ViewScoped
public class ArtikelForm extends AbstractForm<Artikel> {

    NativeSelect<Artikelkategorie> artikelkategorieNativeSelect = new NativeSelect<>();
    TextField bezeichnung = new TextField("Bezeichnung");
    TextArea bezeichnungLang = new TextArea("Bezeichnung Lang");
    TextField mengeneinheit = new TextField("Mengeneinheit");
    FloatField stueckpreis = new FloatField("Stückpreis");
    FloatField anzahl = new FloatField("Anzahl");

    @Inject
    ArtikelkategorieFacade artikelkategorieFacade;

    public ArtikelForm() {
        super(Artikel.class);
    }

    public void lockSelect() {
        artikelkategorieNativeSelect.setEnabled(false);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Artikel");
        openInModalPopup.setWidth("400px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        artikelkategorieNativeSelect.setCaption("Artikelkategorie");
        artikelkategorieNativeSelect.setEmptySelectionAllowed(false);
        artikelkategorieNativeSelect.setItems(artikelkategorieFacade.findAll());
        artikelkategorieNativeSelect.setItemCaptionGenerator(artikelkategorie -> artikelkategorie.getBezeichnung() + " id:" + artikelkategorie.getId());
        return new VerticalLayout(new FormLayout(
                artikelkategorieNativeSelect, bezeichnung, bezeichnungLang, mengeneinheit, stueckpreis, anzahl
        ), getToolbar());
    }


}
