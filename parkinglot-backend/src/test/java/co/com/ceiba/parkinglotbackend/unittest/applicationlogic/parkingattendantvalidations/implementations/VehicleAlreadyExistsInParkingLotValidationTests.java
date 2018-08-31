package co.com.ceiba.parkinglotbackend.unittest.applicationlogic.parkingattendantvalidations.implementations;

import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.implementations.VehicleAlreadyExistsInParkingLotValidation;
import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.core.services.InvoiceService;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvoiceDataException;
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

    @Mock
    private InvoiceService mockInvoiceService;

    // Objects
    private VehicleAlreadyExistsInParkingLotValidation sut;
    private Vehicle vehicle;
    private VehicleTestDataBuilder vehicleTestDataBuilder;
    private InvoiceTestDataBuilder invoiceTestDataBuilder;
    private Invoice invoice;

    @Before
    public void setUp() {
        vehicleTestDataBuilder = new VehicleTestDataBuilder();
        invoiceTestDataBuilder = new InvoiceTestDataBuilder();
        vehicle = vehicleTestDataBuilder.build();
        invoice = invoiceTestDataBuilder.build();
    }

    @After
    public void tearDown() {
        vehicle = null;
        vehicleTestDataBuilder = null;
        invoice = null;
        invoiceTestDataBuilder = null;
    }

    @Test
    public void validateVehicleDoesNotExistInParkingLot() throws VehicleAlreadyExistsInParkingLotException, InvoiceDataException {
        when(mockInvoiceService.getVehicleInParking(any())).thenReturn(Optional.ofNullable(null));
        sut = new VehicleAlreadyExistsInParkingLotValidation(mockInvoiceService);
        sut.execute(Optional.ofNullable(vehicle));
    }

    @Test(expected = VehicleAlreadyExistsInParkingLotException.class)
    public void validateVehicleExistsInParkingLot() throws VehicleAlreadyExistsInParkingLotException, InvoiceDataException {
        when(mockInvoiceService.getVehicleInParking(any())).thenReturn(Optional.ofNullable(invoice));
        sut = new VehicleAlreadyExistsInParkingLotValidation(mockInvoiceService);
        sut.execute(Optional.ofNullable(vehicle));
    }
}
