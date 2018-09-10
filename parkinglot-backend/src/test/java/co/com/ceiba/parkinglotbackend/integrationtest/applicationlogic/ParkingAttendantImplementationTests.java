package co.com.ceiba.parkinglotbackend.integrationtest.applicationlogic;

import co.com.ceiba.parkinglotbackend.applicationlogic.ParkingAttendant;
import co.com.ceiba.parkinglotbackend.applicationlogic.implementations.ParkingAttendantImplementation;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.ParkingCalculatorUtil;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.ParkingCalendarUtil;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.ParkingValidation;
import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.core.services.InvoiceService;
import co.com.ceiba.parkinglotbackend.core.services.ParkingRatesService;
import co.com.ceiba.parkinglotbackend.core.services.VehicleService;
import co.com.ceiba.parkinglotbackend.exceptions.BaseException;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleDataException;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleDoesNotExistException;
import co.com.ceiba.parkinglotbackend.testdatabuilder.ParkingCalendarTestDataBuilder;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.VehicleTestDataBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class ParkingAttendantImplementationTests {

    private ParkingAttendant sut;

    @Mock
    private ParkingCalendarUtil mockParkingCalendarUtil;

    @Autowired
    private ParkingCalculatorUtil parkingCalculatorUtil;

    // Services
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ParkingRatesService parkingRatesService;

    // Validations
    @Autowired
    private List<ParkingValidation> parkingValidations;

    // Other objects
    private Invoice invoice;
    private Vehicle vehicle;
    private ParkingCalendarTestDataBuilder parkingCalendarTestDataBuilder;

    public ParkingAttendantImplementationTests() {
        var vehicleTestDataBuilder = new VehicleTestDataBuilder();
        parkingCalendarTestDataBuilder = new ParkingCalendarTestDataBuilder();
        vehicle = vehicleTestDataBuilder.build();
    }

    @Before
    public void setUp() {
        sut = new ParkingAttendantImplementation(invoiceService, vehicleService, parkingRatesService,
                parkingValidations, mockParkingCalendarUtil, parkingCalculatorUtil);
    }

    @After
    public void tearDown() {
        sut = null;
    }

    @Test
    public void vehicleCheckInTest() throws BaseException {
        LocalDateTime entryDate = parkingCalendarTestDataBuilder.withDay(11).build();
        when(mockParkingCalendarUtil.getTodayDate()).thenReturn(entryDate);
        // assign sut?
        invoice = sut.vehicleCheckIn(vehicle);
        assertNotNull("Invoice returned was null", invoice);
        assertEquals("License plates are not equals", invoice.getVehicle().getLicensePlate(),
                vehicle.getLicensePlate().toUpperCase());
        assertEquals("Entry dates are not equals", invoice.getEntryDate(), entryDate);
        assertFalse("Exit date is not null", invoice.getDepartureDate().isPresent());
        assertFalse("Price is not null", invoice.getPrice().isPresent());
    }

    @Test(expected = VehicleDataException.class)
    public void vehicleCheckInWrongParams() throws BaseException {
        sut.vehicleCheckIn(null);
    }

    @Test
    public void vehicleCheckOutTest() throws BaseException {
        LocalDateTime entryDate = parkingCalendarTestDataBuilder.withDay(11).build();
        LocalDateTime departureDate = parkingCalendarTestDataBuilder.withDay(11).build();
        when(mockParkingCalendarUtil.getTodayDate()).thenReturn(entryDate);
        invoice = sut.vehicleCheckIn(vehicle);
        when(mockParkingCalendarUtil.getTodayDate()).thenReturn(departureDate);
        Invoice exitInvoice = sut.vehicleCheckOut(vehicle.getLicensePlate());

        assertNotNull("Invoice generated in check out process is null", exitInvoice);
        assertEquals("License plates are not equals", invoice.getVehicle().getLicensePlate(),
                exitInvoice.getVehicle().getLicensePlate());
        assertEquals("Entry dates are not equals", invoice.getEntryDate(), entryDate);
        assertTrue("Exit date is null", invoice.getDepartureDate().isPresent());
        assertTrue("Price is null", invoice.getPrice().isPresent());
    }

    @Test(expected = VehicleDataException.class)
    public void vehicleCheckOutWrongParams() throws BaseException {
        sut.vehicleCheckOut(null);
    }

    @Test(expected = VehicleDoesNotExistException.class)
    public void vehicleCheckOutWithoutCheckIn() throws BaseException {
        sut.vehicleCheckOut(vehicle.getLicensePlate());
    }
}
