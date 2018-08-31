package co.com.ceiba.parkinglotbackend.unittest.applicationlogic.parkingattendantvalidations;

import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.ParkingValidationFactory;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.implementations.NoSpaceAvailableValidation;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.implementations.VehicleAlreadyExistsInParkingLotValidation;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.implementations.VehicleDataValidation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
public class ParkingValidationFactoryTests {

    private ParkingValidationFactory sut;

    @Mock
    private NoSpaceAvailableValidation noSpaceAvailableValidation;
    @Mock
    private VehicleDataValidation vehicleDataValidation;
    @Mock
    private VehicleAlreadyExistsInParkingLotValidation vehicleAlreadyExistsInParkingLotValidation;

    @Before
    public void setUp() {
        sut = new ParkingValidationFactory(noSpaceAvailableValidation, vehicleDataValidation, vehicleAlreadyExistsInParkingLotValidation);
    }

    @After
    public void tearDown() {
        sut = null;
    }

    @Test
    public void getVehicleDataValidation() {
        Assert.assertTrue("Factory returns incorrect object", sut.getParkingValidation(
                ParkingValidationFactory.ParkingValidationType.VEHICLE_DATA) instanceof VehicleDataValidation);
    }

    @Test
    public void getNoSpaceAvailableValidation() {
        Assert.assertTrue("Factory returns incorrect object", sut.getParkingValidation(
                ParkingValidationFactory.ParkingValidationType.NO_SPACE_AVAILABLE) instanceof NoSpaceAvailableValidation);
    }

    @Test
    public void getVehicleAlreadyExistsInParkingLotValidation() {
        Assert.assertTrue("Factory returns incorrect object", sut.getParkingValidation(
                ParkingValidationFactory.ParkingValidationType.VEHICLE_ALREADY_EXISTS_IN_PARKING_LOT)
                instanceof VehicleAlreadyExistsInParkingLotValidation);
    }
}
