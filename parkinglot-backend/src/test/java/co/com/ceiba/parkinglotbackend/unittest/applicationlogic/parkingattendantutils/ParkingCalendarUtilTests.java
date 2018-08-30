package co.com.ceiba.parkinglotbackend.unittest.applicationlogic.parkingattendantutils;

import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvalidDatesException;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvalidDayLicensePlateException;
import co.com.ceiba.parkinglotbackend.testdatabuilder.ParkingCalendarTestDataBuilder;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.ParkingCalendarUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class ParkingCalendarUtilTests {

    private ParkingCalendarTestDataBuilder parkingCalendarTestDataBuilder;
    private LocalDateTime sunday;
    private LocalDateTime monday;

    @Before
    public void setUp() {
        parkingCalendarTestDataBuilder = new ParkingCalendarTestDataBuilder();
        sunday = parkingCalendarTestDataBuilder.build();
        monday = parkingCalendarTestDataBuilder.build().plusDays(1);
    }

    @After
    public void tearDown() {
        parkingCalendarTestDataBuilder = null;
        sunday = null;
        monday = null;
    }

    @Test
    public void isSundayOrMondayTest() throws InvalidDayLicensePlateException {
        Assert.assertTrue("Day is not Sunday", ParkingCalendarUtil.isSundayOrMonday(sunday));
        Assert.assertTrue("Day is not Monday", ParkingCalendarUtil.isSundayOrMonday(monday));
    }

    @Test
    public void isNotSundayOrMondayTest() throws InvalidDayLicensePlateException {
        Assert.assertFalse("Day is Sunday",ParkingCalendarUtil.isSundayOrMonday(sunday.minusDays(1)));
        Assert.assertFalse("Day is Monday", ParkingCalendarUtil.isSundayOrMonday(monday.plusDays(1)));
    }

    @Test(expected = InvalidDayLicensePlateException.class)
    public void isSundayOrMondayWithoutEntryDateTest() throws InvalidDayLicensePlateException {
        Assert.assertFalse("Day is Sunday",ParkingCalendarUtil.isSundayOrMonday(null));
    }

    @Test
    public void getCorrectDifferenceTimeTest() throws InvalidDatesException {
        ParkingCalendarUtil.DifferenceTimes differenceTimes = ParkingCalendarUtil.getDifferenceTime(sunday, monday);
        Assert.assertEquals( "Difference time must be one day",1L, differenceTimes.getDays().longValue());
        Assert.assertEquals("Difference time must be zero hours",0L, differenceTimes.getHours().longValue());

        differenceTimes = ParkingCalendarUtil.getDifferenceTime(sunday, sunday);
        Assert.assertEquals("Difference time must be zero days", 0L, differenceTimes.getDays().longValue());
        Assert.assertEquals("Difference time must be zero hours", 0L, differenceTimes.getHours().longValue());

        differenceTimes = ParkingCalendarUtil.getDifferenceTime(sunday, monday.plusDays(5).plusHours(11));
        Assert.assertEquals("Difference time must be 6 days", 6L, differenceTimes.getDays().longValue());
        Assert.assertEquals("Difference time must be 11 hours", 11L, differenceTimes.getHours().longValue());
    }

    @Test
    public void getDifferenceTimeWithoutExtraPriceTest() throws InvalidDatesException {
        ParkingCalendarUtil.DifferenceTimes differenceTimes = ParkingCalendarUtil.getDifferenceTime(sunday, sunday.plusMinutes(5));
        Assert.assertEquals("Difference time must be zero days", 0L, differenceTimes.getDays().longValue());
        Assert.assertEquals("Difference time must be zero hours", 0L, differenceTimes.getHours().longValue());

        differenceTimes = ParkingCalendarUtil.getDifferenceTime(sunday, sunday.plusMinutes(6));
        Assert.assertEquals("Difference time must be zero days", 0L, differenceTimes.getDays().longValue());
        Assert.assertNotEquals("Difference time must not be zero hours", 0L, differenceTimes.getHours().longValue());
    }

    @Test(expected = InvalidDatesException.class)
    public void getDifferenceTimeWithBadDatesTest() throws InvalidDatesException {
        ParkingCalendarUtil.getDifferenceTime(monday, sunday);
    }
}
