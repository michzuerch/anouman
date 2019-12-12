/**
 *
 */
package com.gmail.michzuerch.anouman.ui.utils.converters;

import com.gmail.michzuerch.anouman.test.FormattingTest;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class LocalTimeConverterTest extends FormattingTest {

    @Test
    public void formattingShoudBeLocaleIndependent() {
        LocalTimeConverter converter = new LocalTimeConverter();
        String result = converter.encode(LocalTime.of(13, 9, 33));
        assertEquals("1:09 PM", result);
    }
}
