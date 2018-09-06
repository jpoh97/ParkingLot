package co.com.ceiba.parkinglotbackend.unittest.applicationlogic.parkingattendantvalidations.implementations;

import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.ParkingValidation;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.implementations.VehicleAlreadyExistsInParkingLotValidation;
import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.core.services.InvoiceService;
import co.com.ceiba.parkinglotbackend.exceptions.BaseException;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleAlreadyExistsInParkingLotException;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.InvoiceTestDataBuilder;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.VehicleTestDataBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class VehicleAlreadyExistsInParkingLotValidationTests {

    private ParkingValidation sut;

    @Mock
    private InvoiceService mockInvoiceService;

    // Objects
    private Vehicle vehicle;
    private Invoice invoice;

    public VehicleAlreadyExistsInParkingLotValidationTests() {
        VehicleTestDataBuilder vehicleTestDataBuilder = new VehicleTestDataBuilder();
        InvoiceTestDataBuilder invoiceTestDataBuilder = new InvoiceTestDataBuilder();
        vehicle = vehicleTestDataBuilder.build();
        invoice = invoiceTestDataBuilder.build();
    }

    @Before
    public void setUp() {
        sut = new VehicleAlreadyExistsInParkingLotValidation(mockInvoiceService);
    }

    @After
    public void tearDown() {
        sut = null;
    }

    @Test
    public void validateVehicleDoesNotExistInParkingLot() throws BaseException {
        when(mockInvoiceService.getVehicleInParking(any())).thenReturn(Optional.empty());
        sut.execute(Optional.ofNullable(vehicle));
    }

    @Test(expected = VehicleAlreadyExistsInParkingLotException.class)
    public void validateVehicleExistsInParkingLot() throws BaseException {
        when(mockInvoiceService.getVehicleInParking(any())).thenReturn(Optional.ofNullable(invoice));
        sut.execute(Optional.ofNullable(vehicle));
    }
}
