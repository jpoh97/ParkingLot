package co.com.ceiba.parkinglotbackend.unittest.applicationlogic.parkingattendantvalidations;

import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.ParkingValidationFactory;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.implementations.NoSpaceAvailableValidation;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.implementations.VehicleAlreadyExistsInParkingLotValidation;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.implementations.VehicleDataValidation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingValidationFactoryTests {

    @Autowired
    private ParkingValidationFactory sut;

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
