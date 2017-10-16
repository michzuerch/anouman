package ch.internettechnik.anouman.presentation.ui;

import com.vaadin.data.Validator;
import com.vaadin.ui.AbstractField;

public class TestField2 extends AbstractField<Float> {
    @Override
    protected void doSetValue(Float value) {

    }

    @Override
    public Float getValue() {
        return null;
    }

    @Override
    public Validator<Float> getDefaultValidator() {
        return null;
    }
}
