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
public class EsrToolFormatBetragLeftTest {
    @Parameterized.Parameter
    public String p1;
    @Parameterized.Parameter(value = 1)
    public String p2;
    private EsrTool tool = new EsrTool();

    //@org.jetbrains.annotations.NotNull
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"125.56", "125"},
                {"5.99", "5"},
                {"0.00", "0"},
                {"1.00", "1"},
                {"7.03", "7"},
                {"7.02", "7"},
                {"0.01", "0"},
                {"0.02", "0"},
                {"62341.03", "62341"},
                {"312.52", "312"},
                {"999.98", "999"},
        };
        return Arrays.asList(data);
    }

    @Test
    public void testFormatEsrBetragLeft() {
        assertEquals("EsrTool formatEsrBetragLeft", tool.formatEsrBetragLeft(p1), p2);
    }
}
