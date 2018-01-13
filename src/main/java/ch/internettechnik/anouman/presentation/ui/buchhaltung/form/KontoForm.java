package ch.internettechnik.anouman.presentation.ui.buchhaltung.form;

import ch.internettechnik.anouman.backend.entity.Konto;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

public class KontoForm extends AbstractForm<Konto> {
    TextField bezeichnung = new TextField("Bezeichnung");
    TextField kontonummer = new TextField("Kontonummer");
    TextArea bemerkung = new TextArea("Bemerkung");

    public KontoForm() {
        super(Konto.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Konto");
        openInModalPopup.setWidth("400px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        return new VerticalLayout(new FormLayout(bezeichnung, kontonummer, bemerkung), getToolbar());
    }


}
