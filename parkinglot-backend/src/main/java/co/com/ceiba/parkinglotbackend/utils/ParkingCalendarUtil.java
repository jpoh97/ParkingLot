package co.com.ceiba.parkinglotbackend.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ParkingCalendarUtil {

    private static final Integer MINUTES_IN_A_HOUR = 60;
    private static final Integer HOURS_IN_A_DAY = 24;
    private static final Integer MINUTES_IN_A_DAY = MINUTES_IN_A_HOUR * HOURS_IN_A_DAY;

    public static boolean isSundayOrMonday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.MONDAY;
    }

    public static DifferenceTimes getDifferenceTime(LocalDate entryDate, LocalDate departureDate) {
        Long minutes = getDifferenceMinutes(entryDate, departureDate);
        Long days = minutes / MINUTES_IN_A_DAY;
        Long remainingMinutes = minutes - days * MINUTES_IN_A_DAY;
        Long hours = remainingMinutes / 60;
        if(remainingMinutes % 60 != 0) {
            hours++;
        }
        return new DifferenceTimes(days, hours);
    }

    private static Long getDifferenceMinutes(LocalDate entryDate, LocalDate departureDate) {
        return ChronoUnit.MINUTES.between(departureDate, entryDate);
    }

    @Data
    @AllArgsConstructor
    public static class DifferenceTimes {
        private Long days;
        private Long hours;
    }
}
