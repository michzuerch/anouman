package ch.internettechnik.anouman.presentation.ui.templatebuchhaltung.form;

import ch.internettechnik.anouman.backend.entity.TemplateKontoart;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

@ViewScoped
public class TemplateKontoartForm extends AbstractForm<TemplateKontoart> {
    TextField bezeichnung = new TextField("Bezeichnung");
    TextField kontonummer = new TextField("Kontonummer");

    public TemplateKontoartForm() {
        super(TemplateKontoart.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Template Kontoart");
        openInModalPopup.setWidth("400px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        return new VerticalLayout(new FormLayout(bezeichnung, kontonummer), getToolbar());
    }


}
