package ch.internettechnik.anouman.presentation.ui.templatebuchhaltung.form;

import ch.internettechnik.anouman.backend.entity.TemplateKontogruppe;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

@ViewScoped
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
