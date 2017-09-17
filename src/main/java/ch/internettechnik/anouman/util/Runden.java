package ch.internettechnik.anouman.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Runden {

    public static BigDecimal runden(BigDecimal val) {


        BigDecimal twenty = new BigDecimal(20);
        //BigDecimal amount = new BigDecimal(val); // To round to the nearest .05, multiply by 20, round to the nearest integer, then divide by 20

        BigDecimal result = new BigDecimal(val.multiply(twenty)
                .add(new BigDecimal("0.5"))
                .toBigInteger()).divide(twenty);
        return result;
    }

    public static double runden(double val) {

        BigDecimal twenty = new BigDecimal(20);
        //BigDecimal amount = new BigDecimal(val); // To round to the nearest .05, multiply by 20, round to the nearest integer, then divide by 20

        BigDecimal val2 = new BigDecimal(val);
        BigDecimal result = new BigDecimal(val2.multiply(twenty)
                .add(new BigDecimal("0.5"))
                .toBigInteger()).divide(twenty);
        return result.doubleValue();
    }

    public static String runden(String val) {
        Locale loc = new Locale("de_CH");
        NumberFormat df = NumberFormat.getInstance(loc);
        df.setGroupingUsed(false);
        df.setMinimumFractionDigits(2);
        BigDecimal h1 = new BigDecimal(val);
        BigDecimal r = runden(h1);

        return df.format(r);
    }
}
