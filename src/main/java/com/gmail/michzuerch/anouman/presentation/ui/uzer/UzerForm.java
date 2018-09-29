package com.gmail.michzuerch.anouman.presentation.ui.uzer;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Uzer;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

import java.util.logging.Logger;

/**
 * Created by michzuerch on 09.08.15.
 */
public class UzerForm extends AbstractForm<Uzer> {
    private static final Logger LOGGER = Logger.getLogger(UzerForm.class.getName());

    TextField principal = new TextField("principal");
    PasswordField pazzword = new PasswordField("password");
    TextField description = new TextField("description");

    public UzerForm() {
        super(Uzer.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setWidth("400px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        return new VerticalLayout(new FormLayout(
                principal, pazzword, description
        ), getToolbar());
    }
}
