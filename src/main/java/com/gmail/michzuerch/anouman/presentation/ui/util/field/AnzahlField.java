package com.gmail.michzuerch.anouman.presentation.ui.util.field;

import org.vaadin.ui.NumberField;

import java.util.Locale;

public class AnzahlField extends NumberField {
    public AnzahlField() {
        super();
        setLocale(Locale.GERMAN);
        setDecimalPrecision(2);
        setGroupingSeparator('\'');
        setGroupingUsed(false);
        setDecimalSeparator('.');
        setDecimalSeparatorAlwaysShown(false);
        setMinimumFractionDigits(0);
        //setMinValue(5);


    }

    public AnzahlField(String caption) {
        super();
        setCaption(caption);
        setLocale(Locale.GERMAN);
        setDecimalPrecision(2);
        setGroupingSeparator('\'');
        setGroupingUsed(false);
        setDecimalSeparator('.');
        setDecimalSeparatorAlwaysShown(false);
        setMinimumFractionDigits(0);

        //setMinValue(5);
    }

}
