package com.gmail.michzuerch.anouman.ui.utils.converters;

import com.gmail.michzuerch.anouman.ui.dataproviders.DataProviderUtil;
import com.gmail.michzuerch.anouman.ui.utils.FormattingUtils;
import com.vaadin.flow.templatemodel.ModelEncoder;

public class CurrencyFormatter implements ModelEncoder<Integer, String> {

    private static final long serialVersionUID = 1L;

    @Override
    public String encode(Integer modelValue) {
        return DataProviderUtil.convertIfNotNull(modelValue, FormattingUtils::formatAsCurrency);
    }

    @Override
    public Integer decode(String presentationValue) {
        throw new UnsupportedOperationException();
    }
}
