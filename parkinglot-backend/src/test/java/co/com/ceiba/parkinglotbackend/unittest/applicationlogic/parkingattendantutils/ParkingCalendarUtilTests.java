package co.com.ceiba.parkinglotbackend.unittest.applicationlogic.parkingattendantutils;

import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvalidDatesException;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvalidDayLicensePlateException;
import co.com.ceiba.parkinglotbackend.testdatabuilder.ParkingCalendarTestDataBuilder;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.ParkingCalendarUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ParkingCalendarUtilTests {

    private ParkingCalendarUtil sut;

    private ParkingCalendarTestDataBuilder parkingCalendarTestDataBuilder;
    private LocalDateTime sunday;
    private LocalDateTime monday;

    @Before
    public void setUp() {
        parkingCalendarTestDataBuilder = new ParkingCalendarTestDataBuilder();
        sunday = parkingCalendarTestDataBuilder.build();
        monday = parkingCalendarTestDataBuilder.build().plusDays(1);
        sut = new ParkingCalendarUtil();
    }

    @After
    public void tearDown() {
        sut = null;
        parkingCalendarTestDataBuilder = null;
        sunday = null;
        monday = null;
    }

    @Test
    public void isSundayOrMondayTest() throws InvalidDayLicensePlateException {
        assertTrue("Day is not Sunday", sut.isSundayOrMonday(sunday));
        assertTrue("Day is not Monday", sut.isSundayOrMonday(monday));
    }

    @Test
    public void isNotSundayOrMondayTest() throws InvalidDayLicensePlateException {
        assertFalse("Day is Sunday",sut.isSundayOrMonday(sunday.minusDays(1)));
        assertFalse("Day is Monday", sut.isSundayOrMonday(monday.plusDays(1)));
    }

    @Test(expected = InvalidDayLicensePlateException.class)
    public void isSundayOrMondayWithoutEntryDateTest() throws InvalidDayLicensePlateException {
        assertFalse("Day is Sunday", sut.isSundayOrMonday(null));
    }

    @Test
    public void getCorrectDifferenceTimeTest() throws InvalidDatesException {
        ParkingCalendarUtil.DifferenceTimes differenceTimes = sut.getDifferenceTime(sunday, monday);
        assertEquals( "Difference time must be one day",1L, differenceTimes.getDays().longValue());
        assertEquals("Difference time must be zero hours",0L, differenceTimes.getHours().longValue());

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
