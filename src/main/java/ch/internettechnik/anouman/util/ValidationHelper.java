package ch.internettechnik.anouman.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidationHelper {

    public static boolean isValidNumber(Class c, String numString) {
        try {
            if (c == double.class || c == Double.class) {
                Double.parseDouble(numString);
            } else if (c == int.class || c == Integer.class) {
                Integer.parseInt(numString);
            } else if (c == float.class || c == Float.class) {
                Float.parseFloat(numString);
            } else if (c == long.class || c == Long.class) {
                Long.parseLong(numString);
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public static boolean isValidDate(String val) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatter.parse(val);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}