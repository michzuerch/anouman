package com.gmail.michzuerch.anouman.ui.utils.converters;

import com.gmail.michzuerch.anouman.test.FormattingTest;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class LocalDateTimeConverterTest extends FormattingTest {
    @Test
    public void formattingShoudBeLocaleIndependent() {
        LocalDateTimeConverter converter = new LocalDateTimeConverter();
        String result = converter.encode(LocalDateTime.of(2016, 11, 27, 22, 15, 33));
        assertEquals("27.11.2016 10:15 PM", result);
    }
}
