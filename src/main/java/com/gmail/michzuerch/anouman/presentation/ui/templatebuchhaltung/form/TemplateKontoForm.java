package com.gmail.michzuerch.anouman.presentation.ui.templatebuchhaltung.form;

import com.gmail.michzuerch.anouman.backend.jpa.domain.TemplateKonto;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

public class TemplateKontoForm extends AbstractForm<TemplateKonto> {
    TextField bezeichnung = new TextField("Bezeichnung");
    TextField kontonummer = new TextField("Kontonummer");
    TextArea bemerkung = new TextArea("Bemerkung");

    public TemplateKontoForm() {
        super(TemplateKonto.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Template Konto");
        openInModalPopup.setWidth("400px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        return new VerticalLayout(new FormLayout(bezeichnung, kontonummer, bemerkung), getToolbar());
    }


}
