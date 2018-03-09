package com.gmail.michzuerch.anouman.presentation.ui.artikelbild;

import com.gmail.michzuerch.anouman.backend.entity.Artikel;
import com.gmail.michzuerch.anouman.backend.entity.Artikelbild;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.ArtikelDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.ArtikelbildDeltaspikeFacade;
import com.gmail.michzuerch.anouman.presentation.ui.util.field.ImageField;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

import javax.inject.Inject;

public class ArtikelbildForm extends AbstractForm<Artikelbild> {
    @Inject
    ArtikelDeltaspikeFacade artikelDeltaspikeFacade;

    @Inject
    ArtikelbildDeltaspikeFacade artikelbildDeltaspikeFacade;

    NativeSelect<Artikel> artikel = new NativeSelect<>();
    TextField titel = new TextField("Titel");
    ImageField bild = new ImageField("Bild");

    public ArtikelbildForm() {
        super(Artikelbild.class);
    }

    public void lockSelect() {
        artikel.setEnabled(false);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Artikelbild");
        openInModalPopup.setWidth("500px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        artikel.setCaption("Artikel");
        artikel.setItemCaptionGenerator(artikel1 -> artikel1.getBezeichnung() + " id:" + artikel1.getId());
        artikel.setItems(artikelDeltaspikeFacade.findAll());
        artikel.setEmptySelectionAllowed(false);
        return new VerticalLayout(new FormLayout(
                artikel, titel, bild
        ), getToolbar());
    }
}
