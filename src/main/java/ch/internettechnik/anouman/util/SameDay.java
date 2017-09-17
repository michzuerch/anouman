package ch.internettechnik.anouman.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by michzuerch on 24.07.15.
 */
public class SameDay {
    public static boolean sameDay(Date day1, Date day2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(day1).equals(fmt.format(day2));
    }
}
