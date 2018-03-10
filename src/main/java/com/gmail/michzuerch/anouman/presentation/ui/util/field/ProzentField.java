package com.gmail.michzuerch.anouman.presentation.ui.util.field;

import org.vaadin.ui.NumberField;

import java.util.Locale;

public class ProzentField extends NumberField {
    public ProzentField() {
        super();
        setLocale(Locale.GERMAN);
        setGroupingUsed(false);
        setDecimalPrecision(1);
        setDecimalSeparator('.');
        setDecimalSeparatorAlwaysShown(true);
        setMinimumFractionDigits(0);
        //setMinValue(5);


    }

    public ProzentField(String caption) {
        super();
        setCaption(caption);
        setLocale(Locale.GERMAN);
        setGroupingUsed(false);
        setDecimalPrecision(1);
        setDecimalSeparator('.');
        setDecimalSeparatorAlwaysShown(true);
        setMinimumFractionDigits(0);
        //setMinValue(5);


    }
}
