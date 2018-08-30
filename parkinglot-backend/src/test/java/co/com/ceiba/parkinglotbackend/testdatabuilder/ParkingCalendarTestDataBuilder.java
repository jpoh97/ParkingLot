package co.com.ceiba.parkinglotbackend.testdatabuilder;

import java.time.LocalDateTime;
import java.time.Month;

public class ParkingCalendarTestDataBuilder {

    private static final Integer YEAR = 1997;
    private static final Month MONTH = Month.FEBRUARY;
    private static final Integer DAY = 9;
    private static final Integer HOUR = 0;
    private static final Integer MINUTE = 0;

    private Integer year = 1997;
    private Month month = Month.FEBRUARY;
    private Integer day = 9;
    private Integer hour = 0;
    private Integer minute = 0;

    public ParkingCalendarTestDataBuilder() {
        this.year = YEAR;
        this.month = MONTH;
        this.day = DAY;
        this.hour = HOUR;
        this.minute = MINUTE;
    }

    public ParkingCalendarTestDataBuilder withYear(Integer year) {
        this.year = year;
        return this;
    }

    public ParkingCalendarTestDataBuilder withMonth(Month month) {
        this.month = month;
        return this;
    }

    public ParkingCalendarTestDataBuilder withDay(Integer day) {
        this.day = day;
        return this;
    }

    public ParkingCalendarTestDataBuilder withHour(Integer hour) {
        this.hour = hour;
        return this;
    }

    public ParkingCalendarTestDataBuilder withMinute(Integer minute) {
        this.year = minute;
        return this;
    }

    public LocalDateTime build() {
        return LocalDateTime.of(this.year, this.month, this.day, this.hour, this.minute);
    }
}
