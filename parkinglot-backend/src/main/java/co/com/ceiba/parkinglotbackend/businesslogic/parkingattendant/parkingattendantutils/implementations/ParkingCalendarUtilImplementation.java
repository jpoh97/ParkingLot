package co.com.ceiba.parkinglotbackend.businesslogic.parkingattendant.parkingattendantutils.implementations;

import co.com.ceiba.parkinglotbackend.businesslogic.parkingattendant.parkingattendantutils.ParkingCalendarUtil;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvalidDatesException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class ParkingCalendarUtilImplementation implements ParkingCalendarUtil {

    private static final Long MINUTES_WITHOUT_EXTRA_PRICE = 5L;
    private static final Integer MINUTES_IN_A_HOUR = 60;
    private static final Integer HOURS_IN_A_DAY = 24;
    private static final Integer MINUTES_IN_A_DAY = MINUTES_IN_A_HOUR * HOURS_IN_A_DAY;

    public boolean isSundayOrMondayToday() {
        DayOfWeek dayOfWeek = getTodayDate().getDayOfWeek();
        return dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.MONDAY;
    }

    /**
     * The first 5 minutes do not matter.
     *
     * @param entryDate     at parking lot
     * @param departureDate at parking lot
     * @return time in days and hours
     */
    public DifferenceTimes getDifferenceTime(LocalDateTime entryDate, LocalDateTime departureDate) throws InvalidDatesException {
        Long minutes = getDifferenceMinutes(entryDate, departureDate);
        if (minutes < 0) {
            throw new InvalidDatesException();
        }
        if (minutes <= MINUTES_WITHOUT_EXTRA_PRICE) {
            return new DifferenceTimes(0L, 0L);
        }
        Long days = minutes / MINUTES_IN_A_DAY;
        Long remainingMinutes = minutes - days * MINUTES_IN_A_DAY;
        Long hours = remainingMinutes / MINUTES_IN_A_HOUR;
        if (remainingMinutes % MINUTES_IN_A_HOUR != 0) {
            hours++;
        }
        return new DifferenceTimes(days, hours);
    }

    public LocalDateTime getTodayDate() {
        return LocalDateTime.now();
    }

    private Long getDifferenceMinutes(LocalDateTime entryDate, LocalDateTime departureDate) {
        return ChronoUnit.MINUTES.between(entryDate, departureDate);
    }
}
