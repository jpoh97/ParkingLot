package co.com.ceiba.parkinglotbackend.unittest.businesslogic.parkingattendant.parkingattendantvalidations.implementations;

import co.com.ceiba.parkinglotbackend.businesslogic.parkingattendant.parkingattendantvalidations.ParkingValidation;
import co.com.ceiba.parkinglotbackend.businesslogic.parkingattendant.parkingattendantvalidations.implementations.NoSpaceAvailableValidation;
import co.com.ceiba.parkinglotbackend.persistence.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.persistence.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.businesslogic.InvoiceService;
import co.com.ceiba.parkinglotbackend.businesslogic.VehicleTypeService;
import co.com.ceiba.parkinglotbackend.exceptions.BaseException;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.NoSpaceAvailableException;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.VehicleTestDataBuilder;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.VehicleTypeTestDataBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class NoSpaceAvailableValidationTests {

    private ParkingValidation sut;

    @Mock
    private InvoiceService mockInvoiceService;

    @Mock
    private VehicleTypeService mockVehicleTypeService;

    // Objects
    private Long spacesInUse;
    private VehicleType vehicleType;
    private Vehicle vehicle;

    public NoSpaceAvailableValidationTests() {
        var vehicleTypeTestDataBuilder = new VehicleTypeTestDataBuilder();
        var vehicleTestDataBuilder = new VehicleTestDataBuilder();
        vehicle = vehicleTestDataBuilder.build();
        vehicleType = vehicleTypeTestDataBuilder.build();
    }

    @Before
    public void setUp() {
        spacesInUse = 7L;
        sut = new NoSpaceAvailableValidation(mockInvoiceService, mockVehicleTypeService);
    }

    @After
    public void tearDown() {
        sut = null;
    }

    @Test
    public void validateSpaceAvailable() throws BaseException {
        when(mockInvoiceService.getParkingSpacesCountInUseForVehicleType(any())).thenReturn(spacesInUse);
        when(mockVehicleTypeService.getCurrentVehicleType(any())).thenReturn(Optional.ofNullable(vehicleType));

        sut.execute(Optional.ofNullable(vehicle));
    }

    @Test(expected = NoSpaceAvailableException.class)
    public void validateNotSpaceAvailable() throws BaseException {
        spacesInUse = 10L;
        when(mockInvoiceService.getParkingSpacesCountInUseForVehicleType(any())).thenReturn(spacesInUse);
        when(mockVehicleTypeService.getCurrentVehicleType(any())).thenReturn(Optional.ofNullable(vehicleType));

        sut.execute(Optional.ofNullable(vehicle));
    }

    @Test(expected = NoSpaceAvailableException.class)
    public void validateSpaceAvailableWithoutVehicleType() throws BaseException {
        when(mockInvoiceService.getParkingSpacesCountInUseForVehicleType(any())).thenReturn(spacesInUse);
        when(mockVehicleTypeService.getCurrentVehicleType(any())).thenReturn(Optional.empty());

        sut.execute(Optional.ofNullable(vehicle));
    }
}
