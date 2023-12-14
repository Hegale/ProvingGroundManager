package pg.provingground.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static LocalDateTime convertToStartOfDay(String dateString) {
        System.out.println("dateString = " + dateString);
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        return LocalDateTime.of(date, LocalTime.MIN);
    }

    public static LocalDateTime convertToEndOfDay(String dateString) {
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        return LocalDateTime.of(date, LocalTime.MAX);
    }
}
