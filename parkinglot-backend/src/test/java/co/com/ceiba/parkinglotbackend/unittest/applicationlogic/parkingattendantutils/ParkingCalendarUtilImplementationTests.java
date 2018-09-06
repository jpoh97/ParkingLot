package co.com.ceiba.parkinglotbackend.unittest.applicationlogic.parkingattendantutils;

import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.ParkingCalendarUtil;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvalidDatesException;
import co.com.ceiba.parkinglotbackend.testdatabuilder.ParkingCalendarTestDataBuilder;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.implementations.ParkingCalendarUtilImplementation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ParkingCalendarUtilImplementationTests {

    private ParkingCalendarUtil sut;

    private LocalDateTime sunday;
    private LocalDateTime monday;

    public ParkingCalendarUtilImplementationTests() {
        ParkingCalendarTestDataBuilder parkingCalendarTestDataBuilder = new ParkingCalendarTestDataBuilder();
        sunday = parkingCalendarTestDataBuilder.build();
        monday = parkingCalendarTestDataBuilder.build().plusDays(1);
    }

    @Before
    public void setUp() {
        sut = new ParkingCalendarUtilImplementation();
    }

    @After
    public void tearDown() {
        sut = null;
    }

    @Test
    public void getCorrectDifferenceTimeTest() throws InvalidDatesException {
        ParkingCalendarUtil.DifferenceTimes differenceTimes = sut.getDifferenceTime(sunday, monday);
        assertEquals("Difference time must be one day", 1L, differenceTimes.getDays().longValue());
        assertEquals("Difference time must be zero hours", 0L, differenceTimes.getHours().longValue());

        differenceTimes = sut.getDifferenceTime(sunday, sunday);
        assertEquals("Difference time must be zero days", 0L, differenceTimes.getDays().longValue());
        assertEquals("Difference time must be zero hours", 0L, differenceTimes.getHours().longValue());

        differenceTimes = sut.getDifferenceTime(sunday, monday.plusDays(5).plusHours(11));
        assertEquals("Difference time must be 6 days", 6L, differenceTimes.getDays().longValue());
        assertEquals("Difference time must be 11 hours", 11L, differenceTimes.getHours().longValue());
    }

    @Test
    public void getDifferenceTimeWithoutExtraPriceTest() throws InvalidDatesException {
        ParkingCalendarUtil.DifferenceTimes differenceTimes = sut.getDifferenceTime(sunday, sunday.plusMinutes(5));
        assertEquals("Difference time must be zero days", 0L, differenceTimes.getDays().longValue());
        assertEquals("Difference time must be zero hours", 0L, differenceTimes.getHours().longValue());

        differenceTimes = sut.getDifferenceTime(sunday, sunday.plusMinutes(6));
        assertEquals("Difference time must be zero days", 0L, differenceTimes.getDays().longValue());
        assertNotEquals("Difference time must not be zero hours", 0L, differenceTimes.getHours().longValue());
    }

    @Test(expected = InvalidDatesException.class)
    public void getDifferenceTimeWithBadDatesTest() throws InvalidDatesException {
        sut.getDifferenceTime(monday, sunday);
    }
}
