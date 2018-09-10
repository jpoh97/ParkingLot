package co.com.ceiba.parkinglotbackend.unittest.applicationlogic.parkingattendantvalidations.implementations;

import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.implementations.ParkingCalendarUtilImplementation;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.ParkingValidation;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.exceptions.BaseException;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvalidDayLicensePlateException;
import co.com.ceiba.parkinglotbackend.testdatabuilder.ParkingCalendarTestDataBuilder;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.implementations.InvalidDayLicensePlateValidation;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.VehicleTestDataBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class InvalidDayLicensePlateValidationTests {

    private ParkingValidation sut;

    @Spy
    private ParkingCalendarUtilImplementation spyParkingCalendarUtil;

    private Vehicle vehicle;
    private LocalDateTime sunday;
    private LocalDateTime monday;
    private static final String LICENSE_PLATE_START_WITH_A = "abc42a";
    private static final String LICENSE_PLATE_DOES_NOT_START_WITH_A = "ssh42a";

    public InvalidDayLicensePlateValidationTests() {
        var parkingCalendarTestDataBuilder = new ParkingCalendarTestDataBuilder();
        sunday = parkingCalendarTestDataBuilder.build();
        monday = parkingCalendarTestDataBuilder.build().plusDays(1);
    }

    @Before
    public void setUp() {
        vehicle = new VehicleTestDataBuilder().withLicensePlate(LICENSE_PLATE_START_WITH_A).build();
        sut = new InvalidDayLicensePlateValidation(spyParkingCalendarUtil);
    }

    @After
    public void tearDown() {
        sut = null;
        vehicle = null;
    }

    @Test
    public void validateCorrectSundayDateAndStartWithA() throws BaseException {
        when(spyParkingCalendarUtil.getTodayDate()).thenReturn(sunday);
        sut.execute(Optional.ofNullable(vehicle));
    }

    @Test
    public void validateCorrectMondayDateAndStartWithA() throws BaseException {
        when(spyParkingCalendarUtil.getTodayDate()).thenReturn(monday);
        sut.execute(Optional.ofNullable(vehicle));
    }

    @Test
    public void validateCorrectDateAndDoesNotStartWithA() throws BaseException {
        when(spyParkingCalendarUtil.getTodayDate()).thenReturn(sunday);
        vehicle.setLicensePlate(LICENSE_PLATE_DOES_NOT_START_WITH_A);
        sut.execute(Optional.ofNullable(vehicle));
    }

    @Test(expected = InvalidDayLicensePlateException.class)
    public void validateIncorrectDateAndStartWithA() throws BaseException {
        when(spyParkingCalendarUtil.getTodayDate()).thenReturn(monday.plusDays(1));
        sut.execute(Optional.ofNullable(vehicle));
    }

    @Test
    public void validateIncorrectDateAndDoesNotStartWithA() throws BaseException {
        when(spyParkingCalendarUtil.getTodayDate()).thenReturn(monday.plusDays(1));
        vehicle.setLicensePlate(LICENSE_PLATE_DOES_NOT_START_WITH_A);
        sut.execute(Optional.ofNullable(vehicle));
    }

    @Test(expected = InvalidDayLicensePlateException.class)
    public void validateWithoutLicensePlate() throws BaseException {
        vehicle.setLicensePlate(null);
        sut.execute(Optional.ofNullable(vehicle));
    }
}