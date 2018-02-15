package com.gmail.michzuerch.anouman.presentation.ui.buchhaltung.form;

import com.gmail.michzuerch.anouman.backend.entity.Kontohauptgruppe;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

public class KontohauptgruppeForm extends AbstractForm<Kontohauptgruppe> {
    TextField bezeichnung = new TextField("Bezeichnung");
    TextField kontonummer = new TextField("Kontonummer");


    public KontohauptgruppeForm() {
        super(Kontohauptgruppe.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Kontohauptgruppe");
        openInModalPopup.setWidth("400px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        return new VerticalLayout(new FormLayout(bezeichnung, kontonummer), getToolbar());
    }


}
