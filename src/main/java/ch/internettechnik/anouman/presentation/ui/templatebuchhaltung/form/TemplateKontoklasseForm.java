package ch.internettechnik.anouman.presentation.ui.templatebuchhaltung.form;

import ch.internettechnik.anouman.backend.entity.TemplateKontoklasse;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

@UIScope
public class TemplateKontoklasseForm extends AbstractForm<TemplateKontoklasse> {
    TextField bezeichnung = new TextField("Bezeichnung");
    TextField kontonummer = new TextField("Kontonummer");


    public TemplateKontoklasseForm() {
        super(TemplateKontoklasse.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Template Kontoklasse");
        openInModalPopup.setWidth("400px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        return new VerticalLayout(new FormLayout(bezeichnung, kontonummer), getToolbar());
    }


}
