package com.gmail.michzuerch.anouman.presentation.ui.artikel;

import com.gmail.michzuerch.anouman.backend.entity.Artikel;
import com.gmail.michzuerch.anouman.backend.entity.Artikelkategorie;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.ArtikelkategorieDeltaspikeFacade;
import com.gmail.michzuerch.anouman.presentation.ui.util.field.AnzahlField;
import com.gmail.michzuerch.anouman.presentation.ui.util.field.BetragField;
import com.vaadin.ui.*;
import org.vaadin.ui.NumberField;
import org.vaadin.viritin.form.AbstractForm;

import javax.inject.Inject;

public class ArtikelForm extends AbstractForm<Artikel> {

    NativeSelect<Artikelkategorie> artikelkategorie = new NativeSelect<>();
    TextField bezeichnung = new TextField("Bezeichnung");
    TextArea bezeichnungLang = new TextArea("Bezeichnung Lang");
    TextField mengeneinheit = new TextField("Mengeneinheit");
    BetragField stueckpreis = new BetragField("Stückpreis");
    AnzahlField anzahl = new AnzahlField("Anzahl");

    @Inject
    ArtikelkategorieDeltaspikeFacade artikelkategorieDeltaspikeFacade;

    public ArtikelForm() {
        super(Artikel.class);
    }

    public void lockSelect() {
        artikelkategorie.setEnabled(false);
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
                NumberField.getConverter("Muss Betrag sein")
        ).bind("stueckpreis");

        getBinder().forField(anzahl).withConverter(
                NumberField.getConverter("Muss Anzahl sein")
        ).bind("anzahl");

        artikelkategorie.setCaption("Artikelkategorie");
        artikelkategorie.setEmptySelectionAllowed(false);
        artikelkategorie.setItems(artikelkategorieDeltaspikeFacade.findAll());
        artikelkategorie.setItemCaptionGenerator(artikelkategorie -> artikelkategorie.getBezeichnung() + " id:" + artikelkategorie.getId());
        return new VerticalLayout(new FormLayout(
                artikelkategorie, bezeichnung, bezeichnungLang, mengeneinheit, stueckpreis, anzahl
        ), getToolbar());
    }


}
