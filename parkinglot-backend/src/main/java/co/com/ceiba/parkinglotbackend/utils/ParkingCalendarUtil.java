package co.com.ceiba.parkinglotbackend.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingCalendarUtil {

    private static final Integer MINUTES_IN_A_HOUR = 60;
    private static final Integer HOURS_IN_A_DAY = 24;
    private static final Integer MINUTES_IN_A_DAY = MINUTES_IN_A_HOUR * HOURS_IN_A_DAY;

    public static boolean isSundayOrMonday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.MONDAY;
    }

    public static DifferenceTimes getDifferenceTime(LocalDateTime entryDate, LocalDateTime departureDate) {
        Long minutes = getDifferenceMinutes(entryDate, departureDate);
        Long days = minutes / MINUTES_IN_A_DAY;
        Long remainingMinutes = minutes - days * MINUTES_IN_A_DAY;
        Long hours = remainingMinutes / MINUTES_IN_A_HOUR;
        if(remainingMinutes % MINUTES_IN_A_HOUR != 0) {
            hours++;
        }
        return new DifferenceTimes(days, hours);
    }

    private static Long getDifferenceMinutes(LocalDateTime entryDate, LocalDateTime departureDate) {
        return ChronoUnit.MINUTES.between(departureDate, entryDate);
    }

    public static class DifferenceTimes {
        private Long days;
        private Long hours;

        public DifferenceTimes(Long days, Long hours) {
            this.days = days;
            this.hours = hours;
        }

        public Long getDays() {
            return days;
        }

        public Long getHours() {
            return hours;
        }
    }
}
