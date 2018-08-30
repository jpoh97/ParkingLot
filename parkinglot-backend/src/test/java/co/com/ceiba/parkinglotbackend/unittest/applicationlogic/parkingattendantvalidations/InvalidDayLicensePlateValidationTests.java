package co.com.ceiba.parkinglotbackend.unittest.applicationlogic.parkingattendantvalidations;

import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvalidDayLicensePlateException;
import co.com.ceiba.parkinglotbackend.testdatabuilder.ParkingCalendarTestDataBuilder;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.InvalidDayLicensePlateValidation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class InvalidDayLicensePlateValidationTests {

    private InvalidDayLicensePlateValidation sut;
    private String licensePlate;
    private LocalDateTime sunday;
    private LocalDateTime monday;
    private ParkingCalendarTestDataBuilder parkingCalendarTestDataBuilder;
    private static final String LICENSE_PLATE_START_WITH_A = "abc42a";
    private static final String LICENSE_PLATE_DOES_NOT_START_WITH_A = "ssh42a";

    @Before
    public void setUp() {
        sut = new InvalidDayLicensePlateValidation();
        licensePlate = LICENSE_PLATE_START_WITH_A;
        parkingCalendarTestDataBuilder = new ParkingCalendarTestDataBuilder();
        sunday = parkingCalendarTestDataBuilder.build();
        monday = parkingCalendarTestDataBuilder.build().plusDays(1);
    }

    @After
    public void tearDown() {
        sut = null;
        licensePlate = null;
        sunday = null;
        monday = null;
        parkingCalendarTestDataBuilder = null;
    }

    @Test
    public void validateCorrectDateAndStartWithA() throws InvalidDayLicensePlateException {
        sut.execute(licensePlate, sunday);
        sut.execute(licensePlate, monday);
    }

    @Test
    public void validateCorrectDateAndDoesNotStartWithA() throws InvalidDayLicensePlateException {
        licensePlate = LICENSE_PLATE_DOES_NOT_START_WITH_A;
        sut.execute(licensePlate, sunday);
    }

    @Test(expected = InvalidDayLicensePlateException.class)
    public void validateIncorrectDateAndStartWithA() throws InvalidDayLicensePlateException {
        sut.execute(licensePlate, monday.plusDays(1));
    }

    @Test
    public void validateIncorrectDateAndDoesNotStartWithA() throws InvalidDayLicensePlateException {
        licensePlate = LICENSE_PLATE_DOES_NOT_START_WITH_A;
        sut.execute(licensePlate, monday.plusDays(1));
    }

    @Test(expected = InvalidDayLicensePlateException.class)
    public void validateWithoutLicensePlate() throws InvalidDayLicensePlateException {
        sut.execute(null, sunday);
    }

    @Test(expected = InvalidDayLicensePlateException.class)
    public void validateWithoutEntryDate() throws InvalidDayLicensePlateException {
        sut.execute(licensePlate, null);
    }

    @Test(expected = InvalidDayLicensePlateException.class)
    public void validateWithoutEntryDateAndWithoutLicensePlate() throws InvalidDayLicensePlateException {
        sut.execute(null, null);
    }
}
