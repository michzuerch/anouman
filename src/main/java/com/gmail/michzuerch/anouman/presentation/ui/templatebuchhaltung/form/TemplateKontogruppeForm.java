package com.gmail.michzuerch.anouman.presentation.ui.templatebuchhaltung.form;

import com.gmail.michzuerch.anouman.backend.jpa.domain.TemplateKontogruppe;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

public class TemplateKontogruppeForm extends AbstractForm<TemplateKontogruppe> {
    TextField bezeichnung = new TextField("Bezeichnung");
    TextField kontonummer = new TextField("Kontonummer");

    public TemplateKontogruppeForm() {
        super(TemplateKontogruppe.class);
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
