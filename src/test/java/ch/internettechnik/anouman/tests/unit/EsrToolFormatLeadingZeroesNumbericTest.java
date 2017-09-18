package ch.internettechnik.anouman.tests.unit;

import ch.internettechnik.anouman.util.EsrTool;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: michzuerch
 * Date: 10.08.14
 * Time: 19:22
 * To change this template use File | Settings | File Templates.
 */
public class EsrToolFormatLeadingZeroesNumbericTest {
    EsrTool tool = new EsrTool();

    @Test
    public void testFormatLeadingZeroesNumeric() {
        assertEquals("testFormatLeadingZeroesNumeric", tool.formatLeadingZeroesNumeric("5234.00", 8), "00523400");

    }
}
