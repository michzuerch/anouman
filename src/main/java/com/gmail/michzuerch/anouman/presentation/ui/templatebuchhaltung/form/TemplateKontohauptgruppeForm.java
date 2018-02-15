package com.gmail.michzuerch.anouman.presentation.ui.templatebuchhaltung.form;

import com.gmail.michzuerch.anouman.backend.entity.TemplateKontohauptgruppe;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

public class TemplateKontohauptgruppeForm extends AbstractForm<TemplateKontohauptgruppe> {
    TextField bezeichnung = new TextField("Bezeichnung");
    TextField kontonummer = new TextField("Kontonummer");


    public TemplateKontohauptgruppeForm() {
        super(TemplateKontohauptgruppe.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Template Kontohauptgruppe");
        openInModalPopup.setWidth("400px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        return new VerticalLayout(new FormLayout(bezeichnung, kontonummer), getToolbar());
    }


}
