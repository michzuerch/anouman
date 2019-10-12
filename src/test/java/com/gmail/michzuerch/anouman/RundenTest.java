package com.gmail.michzuerch.anouman;

import com.gmail.michzuerch.anouman.util.Runden;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: michzuerch
 * Date: 18.10.12
 * Time: 15:43
 */

@RunWith(Parameterized.class)
public class RundenTest {

    @Parameterized.Parameter
    public String p1;
    @Parameterized.Parameter(value = 1)
    public String p2;

    //@org.jetbrains.annotations.NotNull
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"125.56", "125.55"},
                {"5.99", "6.00"},
                {"0.00", "0.00"},
                {"1.00", "1.00"},
                {"7.03", "7.05"},
                {"7.02", "7.00"},
                {"0.01", "0.00"},
                {"0.02", "0.00"},
                {"0.03", "0.05"},
                {"999.98", "1000.00"},
        };
        return Arrays.asList(data);
    }


    @Test
    public void testRundenString() {
        assertEquals("Runden String", Runden.runden(p1), p2);

    }

    @Test
    public void testRundenDouble() {
        double d1 = Double.valueOf(p1);
        double d2 = Double.valueOf(p2);
        assertEquals("Runden Double", Runden.runden(d1), d2, 0);
    }
}
