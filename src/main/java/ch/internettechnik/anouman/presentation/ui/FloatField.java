package ch.internettechnik.anouman.presentation.ui;


import com.vaadin.event.FieldEvents;
import org.apache.commons.lang3.StringUtils;
import org.vaadin.viritin.fields.AbstractNumberField;

public class FloatField extends AbstractNumberField<FloatField, Float> {

    private static final long serialVersionUID = 377246000306551089L;

    public FloatField() {
        setSizeUndefined();
    }

    public FloatField(String caption) {
        setCaption(caption);
    }

    @Override
    protected void userInputToValue(String str) {
        if (StringUtils.isNotBlank(str)) {
            value = Float.parseFloat(str);
        } else {
            value = null;
        }
    }

    @Override
    public FloatField withBlurListener(FieldEvents.BlurListener listener) {
        return (FloatField) super.withBlurListener(listener);
    }

    @Override
    public FloatField withFocusListener(FieldEvents.FocusListener listener) {
        return (FloatField) super.withFocusListener(listener);
    }

    @Override
    public Float getValue() {
        return value;
    }

}
