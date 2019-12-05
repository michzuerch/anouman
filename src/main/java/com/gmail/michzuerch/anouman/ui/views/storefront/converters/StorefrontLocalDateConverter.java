package com.gmail.michzuerch.anouman.ui.views.storefront.converters;

import com.vaadin.flow.templatemodel.ModelEncoder;

import java.time.LocalDate;

import static com.gmail.michzuerch.anouman.ui.utils.FormattingUtils.MONTH_AND_DAY_FORMATTER;
import static com.gmail.michzuerch.anouman.ui.utils.FormattingUtils.WEEKDAY_FULLNAME_FORMATTER;

/**
 * Date converter specific for the way date is displayed in storefront.
 */
public class StorefrontLocalDateConverter implements ModelEncoder<LocalDate, StorefrontDate> {

    private static final long serialVersionUID = 1L;

    @Override
    public StorefrontDate encode(LocalDate modelValue) {
        StorefrontDate result = null;
        if (modelValue != null) {
            result = new StorefrontDate();
            result.setDay(MONTH_AND_DAY_FORMATTER.format(modelValue));
            result.setWeekday(WEEKDAY_FULLNAME_FORMATTER.format(modelValue));
            result.setDate(modelValue.toString());
        }
        return result;
    }

    @Override
    public LocalDate decode(StorefrontDate storefrontDate) {
        throw new UnsupportedOperationException();
    }
}
