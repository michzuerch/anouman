package ch.internettechnik.anouman.presentation.ui.field;

import org.vaadin.ui.NumberField;

import java.util.Locale;

public class AnzahlField extends NumberField {
    public AnzahlField(String caption) {
        super();
        setCaption(caption);
        setLocale(Locale.GERMAN);
        setDecimalPrecision(2);
        setDecimalSeparator('.');
        setDecimalSeparatorAlwaysShown(false);
        setMinimumFractionDigits(0);
        //setMinValue(5);


    }
}
