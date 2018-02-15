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
public class EsrToolFormatKlientennummerTest {
    @Parameterized.Parameter
    public String p1;
    @Parameterized.Parameter(value = 1)
    public String p2;
    private EsrTool tool = new EsrTool();

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"70004152", "8"},
                {"010000095580", "4"},
                {"010000001830", "4"},
                {"010000023330", "6"},
                {"010000007330", "4"},
        };
        return Arrays.asList(data);
    }

    @Test
    public void testFormatEsrBetragLeft() {
        assertEquals("EsrTool formatKlientennummer", tool.generatePruefziffer(p1), p2);
    }
}
