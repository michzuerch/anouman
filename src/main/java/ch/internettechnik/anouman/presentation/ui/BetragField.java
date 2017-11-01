package ch.internettechnik.anouman.presentation.ui;


import com.vaadin.event.FieldEvents;
import org.apache.commons.lang3.StringUtils;
import org.vaadin.viritin.fields.AbstractNumberField;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class BetragField extends AbstractNumberField<BetragField, Float> {

    private static final long serialVersionUID = 377246000306551089L;

    public BetragField() {
        setSizeUndefined();
    }

    public BetragField(String caption) {
        setCaption(caption);
    }

    @Override
    protected void configureHtmlElement() {
        s.setProperty("type", getHtmlFieldType());
        // prevent all but numbers with a simple js
        s.setJavaScriptEventHandler("keypress",
                "function(e) {var c = viritin.getChar(e); return c==null || /^[.\\d\\n\\t\\r]+$/.test(c);}");
    }

    @Override
    protected void userInputToValue(String str) {
        if (StringUtils.isNotBlank(str)) {
            value = Float.parseFloat(str);
            NumberFormat.getCurrencyInstance().format(new BigDecimal(Float.parseFloat(str)));

        } else {
            value = null;
        }
    }

    @Override
    public BetragField withBlurListener(FieldEvents.BlurListener listener) {
        return (BetragField) super.withBlurListener(listener);
    }

    @Override
    public BetragField withFocusListener(FieldEvents.FocusListener listener) {
        return (BetragField) super.withFocusListener(listener);
    }

    @Override
    public Float getValue() {
        return value;
    }


}
