package co.com.ceiba.parkinglotbackend.businesslogic.parkingattendant.parkingattendantutils;

import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvalidDatesException;

import java.time.LocalDateTime;

public interface ParkingCalendarUtil {

    boolean isSundayOrMondayToday();

    DifferenceTimes getDifferenceTime(LocalDateTime entryDate, LocalDateTime departureDate) throws InvalidDatesException;

    LocalDateTime getTodayDate();

    class DifferenceTimes {
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
