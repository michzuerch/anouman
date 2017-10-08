package ch.internettechnik.anouman.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

/**
 * Created by michzuerch on 03.07.15.
 */
public class AnzahlFormat {
    private static Logger logger = LoggerFactory.getLogger(AnzahlFormat.class.getName());

    //@todo kein Dezimalpunkt bei ganzen Zahlen
    private static DecimalFormat getDecimalFormat() {
        DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator('\'');
        DecimalFormat df = new DecimalFormat("###.##");
        df.setDecimalFormatSymbols(decimalFormatSymbols);
        df.setGroupingUsed(true);
        df.setGroupingSize(3);
        df.setMinimumIntegerDigits(1);
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(2);
        return df;
    }

    public static Float convertToFloat(String val) {
        if (val == null) return new Float(0f);
        if (val.isEmpty()) return new Float(0f);
        DecimalFormat decimalFormat = getDecimalFormat();
        try {
            return decimalFormat.parse(val).floatValue();
        } catch (ParseException e) {
            logger.error(e.getMessage());
            return new Float(0f);
        }
    }

    public static String formatToString(Float val) {
        DecimalFormat decimalFormat = getDecimalFormat();
        return decimalFormat.format(val);
    }
}
