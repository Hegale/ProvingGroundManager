package pg.provingground.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeUtils {
    public static LocalDateTime convertToStartOfDay(String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        return date.atStartOfDay();
    }

    public static LocalDateTime convertToEndOfDay(String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        return date.atTime(23, 59, 59);
    }
}
