package ch.internettechnik.anouman.presentation.ui.templatebuchhaltung.form;

import ch.internettechnik.anouman.backend.entity.TemplateBuchhaltung;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

@ViewScoped
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
