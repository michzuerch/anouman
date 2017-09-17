package ch.internettechnik.anouman.util;

import org.jboss.logging.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

/**
 * Created by michzuerch on 03.07.15.
 */
public class BetragFormat {
    private static final Logger LOGGER = Logger.getLogger(BetragFormat.class);

    private static DecimalFormat getDecimalFormat() {
        DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator('\'');
        DecimalFormat df = new DecimalFormat("###.##");
        df.setDecimalFormatSymbols(decimalFormatSymbols);
        df.setGroupingUsed(true);
        df.setGroupingSize(3);
        df.setMinimumIntegerDigits(1);
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        return df;
    }

    public static BigDecimal formatToBigDecimal(BigDecimal val) {
        DecimalFormat decimalFormat = getDecimalFormat();
        try {
            return (BigDecimal) decimalFormat.parse(val.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Float convertToFloat(String val) {
        LOGGER.debug("converterToFloat(" + val + ")");
        if (val == null) return new Float(0f);
        if (val.isEmpty()) return new Float(0f);
        DecimalFormat decimalFormat = getDecimalFormat();
        Float result = new Float(0f);
        try {
            result = decimalFormat.parse(val).floatValue();
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    public static String formatToString(BigDecimal val) {
        DecimalFormat decimalFormat = getDecimalFormat();
        return decimalFormat.format(val);
    }

    public static String formatToString(Float val) {
        DecimalFormat decimalFormat = getDecimalFormat();
        return decimalFormat.format(val);
    }

    public static BigDecimal formatToBigDecimal(String val) throws ParseException {
        DecimalFormat decimalFormat = getDecimalFormat();
        return (BigDecimal) decimalFormat.parse(val.toString());
    }


}
