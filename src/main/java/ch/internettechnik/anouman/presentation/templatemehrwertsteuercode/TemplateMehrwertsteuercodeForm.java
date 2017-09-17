package ch.internettechnik.anouman.presentation.templatemehrwertsteuercode;

import ch.internettechnik.anouman.backend.entity.TemplateMehrwertsteuercode;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

@ViewScoped
public class TemplateMehrwertsteuercodeForm extends AbstractForm<TemplateMehrwertsteuercode> {

    TextField bezeichnung = new TextField("Bezeichnung");
    TextField code = new TextField("Code");
    TextField prozent = new TextField("Prozent");

    public TemplateMehrwertsteuercodeForm() {
        super(TemplateMehrwertsteuercode.class);
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
        return new VerticalLayout(new FormLayout(code,bezeichnung,prozent), getToolbar());
    }


}
