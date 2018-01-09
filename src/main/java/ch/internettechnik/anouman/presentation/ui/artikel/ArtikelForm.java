package ch.internettechnik.anouman.presentation.ui.artikel;

import ch.internettechnik.anouman.backend.entity.Artikel;
import ch.internettechnik.anouman.backend.entity.Artikelkategorie;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelkategorieDeltaspikeFacade;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;
import tm.kod.widgets.numberfield.NumberField;

import javax.inject.Inject;

@UIScope
public class ArtikelForm extends AbstractForm<Artikel> {

    NativeSelect<Artikelkategorie> artikelkategorieNativeSelect = new NativeSelect<>();
    TextField bezeichnung = new TextField("Bezeichnung");
    TextArea bezeichnungLang = new TextArea("Bezeichnung Lang");
    TextField mengeneinheit = new TextField("Mengeneinheit");
    NumberField stueckpreis = new NumberField("Stückpreis");
    //FloatField stueckpreis = new FloatField("Stückpreis");
    NumberField anzahl = new NumberField("Anzahl");

    @Inject
    ArtikelkategorieDeltaspikeFacade artikelkategorieDeltaspikeFacade;

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

        getBinder().forField(stueckpreis).withConverter(
                new StringToDoubleConverter("Muss Betrag sein")
        ).bind("stueckpreis");

        getBinder().forField(anzahl).withConverter(
                new StringToDoubleConverter("Muss Anzahl sein")
        ).bind("anzahl");

        stueckpreis.setSigned(false);
        stueckpreis.setUseGrouping(true);
        stueckpreis.setGroupingSeparator('\'');
        stueckpreis.setDecimalLength(2);
        stueckpreis.setDecimalSeparator('.');

        anzahl.setSigned(false);
        anzahl.setUseGrouping(true);
        anzahl.setGroupingSeparator('\'');
        anzahl.setDecimalLength(2);
        anzahl.setDecimalSeparator('.');

        artikelkategorieNativeSelect.setCaption("Artikelkategorie");
        artikelkategorieNativeSelect.setEmptySelectionAllowed(false);
        artikelkategorieNativeSelect.setItems(artikelkategorieDeltaspikeFacade.findAll());
        artikelkategorieNativeSelect.setItemCaptionGenerator(artikelkategorie -> artikelkategorie.getBezeichnung() + " id:" + artikelkategorie.getId());
        return new VerticalLayout(new FormLayout(
                artikelkategorieNativeSelect, bezeichnung, bezeichnungLang, mengeneinheit, stueckpreis, anzahl
        ), getToolbar());
    }


}
