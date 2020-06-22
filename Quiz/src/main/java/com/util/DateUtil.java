package com.util;

import java.time.LocalDate;

public class DateUtil {

    public static boolean isBefore(String first, String second) {
        LocalDate d1 = LocalDate.parse(first);
        LocalDate d2 = LocalDate.parse(second);

        int compare = d1.compareTo(d2);
        return compare < 0;
    }

    public static boolean isLater(String first, String second) {
        LocalDate d1 = LocalDate.parse(first);
        LocalDate d2 = LocalDate.parse(second);

        int compare = d1.compareTo(d2);
        return compare > 0;
    }
}
