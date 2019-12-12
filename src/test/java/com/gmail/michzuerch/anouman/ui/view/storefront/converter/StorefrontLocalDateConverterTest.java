/**
 *
 */
package com.gmail.michzuerch.anouman.ui.view.storefront.converter;

import com.gmail.michzuerch.anouman.test.FormattingTest;
import com.gmail.michzuerch.anouman.ui.views.storefront.converters.StorefrontDate;
import com.gmail.michzuerch.anouman.ui.views.storefront.converters.StorefrontLocalDateConverter;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class StorefrontLocalDateConverterTest extends FormattingTest {

    @Test
    public void formattingShoudBeLocaleIndependent() {
        StorefrontLocalDateConverter converter = new StorefrontLocalDateConverter();
        StorefrontDate result = converter.encode(LocalDate.of(2017, 8, 22));
        assertEquals("Aug 22", result.getDay());
        assertEquals("2017-08-22", result.getDate());
        assertEquals("Tuesday", result.getWeekday());
    }
}
