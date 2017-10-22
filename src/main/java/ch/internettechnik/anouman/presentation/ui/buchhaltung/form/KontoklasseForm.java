package ch.internettechnik.anouman.presentation.ui.buchhaltung.form;

import ch.internettechnik.anouman.backend.entity.Kontoklasse;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

@ViewScoped
public class KontoklasseForm extends AbstractForm<Kontoklasse> {
    TextField bezeichnung = new TextField("Bezeichnung");
    TextField kontonummer = new TextField("Kontonummer");


    public KontoklasseForm() {
        super(Kontoklasse.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Kontoklasse");
        openInModalPopup.setWidth("400px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        return new VerticalLayout(new FormLayout(bezeichnung, kontonummer), getToolbar());
    }


}
