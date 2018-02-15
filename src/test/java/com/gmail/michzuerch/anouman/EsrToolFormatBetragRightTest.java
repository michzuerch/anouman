package com.gmail.michzuerch.anouman;

import com.gmail.michzuerch.anouman.backend.util.EsrTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: michzuerch
 * Date: 10.08.14
 * Time: 19:22
 * To change this template use File | Settings | File Templates.
 */
@RunWith(Parameterized.class)
public class EsrToolFormatBetragRightTest {
    @Parameterized.Parameter
    public String p1;
    @Parameterized.Parameter(value = 1)
    public String p2;
    private EsrTool tool = new EsrTool();

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"125.56", "56"},
                {"5.99", "99"},
                {"0.00", "00"},
                {"1.00", "00"},
                {"7.03", "03"},
                {"7.02", "02"},
                {"0.01", "01"},
                {"0.02", "02"},
                {"62341.03", "03"},
                {"312.52", "52"},
                {"999.98", "98"},
        };
        return Arrays.asList(data);
    }

    @Test
    public void testFormatEsrBetragRight() {
        assertEquals("EsrTool formatEsrBetragRight", tool.formatEsrBetragRight(p1), p2);
    }
}
