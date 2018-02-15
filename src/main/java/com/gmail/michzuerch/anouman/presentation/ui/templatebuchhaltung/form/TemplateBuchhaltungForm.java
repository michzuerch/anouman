package com.gmail.michzuerch.anouman.presentation.ui.templatebuchhaltung.form;

import com.gmail.michzuerch.anouman.backend.entity.TemplateBuchhaltung;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

public class TemplateBuchhaltungForm extends AbstractForm<TemplateBuchhaltung> {

    TextField bezeichnung = new TextField("Bezeichnung");

    public TemplateBuchhaltungForm() {
        super(TemplateBuchhaltung.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Template Buchhaltung");
        openInModalPopup.setWidth("400px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        return new VerticalLayout(new FormLayout(bezeichnung), getToolbar());
    }


}
