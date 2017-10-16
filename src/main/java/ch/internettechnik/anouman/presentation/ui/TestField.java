package ch.internettechnik.anouman.presentation.ui;

import com.vaadin.data.HasValue;
import com.vaadin.shared.Registration;

public class TestField implements HasValue<Float> {
    @Override
    public Float getValue() {
        return null;
    }

    @Override
    public void setValue(Float value) {

    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<Float> listener) {
        return null;
    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return false;
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {

    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public void setReadOnly(boolean readOnly) {

    }
}
