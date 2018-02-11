package ch.internettechnik.anouman.presentation.ui.adresse;

import ch.internettechnik.anouman.backend.entity.Adresse;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.ui.NumberField;
import org.vaadin.viritin.form.AbstractForm;

import java.util.Locale;

//import org.vaadin.ui.NumberField;

/**
 * Created by michzuerch on 09.08.15.
 */
public class AdresseForm extends AbstractForm<Adresse> {
    private static Logger logger = LoggerFactory.getLogger(AdresseForm.class.getName());

    TextField firma = new TextField("Firma");
    TextField anrede = new TextField("Anrede");
    TextField vorname = new TextField("Vorname");
    TextField nachname = new TextField("Nachname");
    TextField strasse = new TextField("Strasse");
    TextField postleitzahl = new TextField("Postleitzahl");
    TextField ort = new TextField("Ort");
    NumberField stundensatz = new NumberField("Stundensatz");


    public AdresseForm() {
        super(Adresse.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Adresse");
        openInModalPopup.setWidth("400px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        stundensatz.setLocale(Locale.GERMAN);
        stundensatz.setDecimalPrecision(2);
        stundensatz.setDecimalSeparator('.');
        stundensatz.setGroupingSeparator('\'');
        stundensatz.setDecimalSeparatorAlwaysShown(true);
        stundensatz.setMinimumFractionDigits(2);
        stundensatz.setMinValue(5);

        getBinder().forField(stundensatz).withConverter(
                NumberField.getConverter("Muss Betrag sein")
        ).bind("stundensatz");

        return new VerticalLayout(new FormLayout(
                firma, anrede, vorname, nachname, strasse, postleitzahl, ort, stundensatz
        ), getToolbar());
    }
}
