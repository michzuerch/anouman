package ch.internettechnik.anouman.util;

public class Validator {
    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isFloat(String str) {
        try {
            float f = Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
