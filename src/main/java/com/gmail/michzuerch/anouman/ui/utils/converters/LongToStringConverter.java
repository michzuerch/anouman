package com.gmail.michzuerch.anouman.ui.utils.converters;

import com.vaadin.flow.templatemodel.ModelEncoder;

public class LongToStringConverter implements ModelEncoder<Long, String> {

    private static final long serialVersionUID = 1L;

    @Override
    public String encode(Long modelValue) {
        if (modelValue == null) {
            return null;
        }
        return modelValue.toString();
    }

    @Override
    public Long decode(String presentationValue) {
        if (presentationValue == null) {
            return null;
        }
        return Long.parseLong(presentationValue);
    }

}
