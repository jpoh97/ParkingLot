package co.com.ceiba.parkinglotbackend.unittest.applicationlogic.parkingattendantvalidations.implementations;

import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.ParkingValidation;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.implementations.VehicleDataValidation;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.exceptions.BaseException;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleDataException;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.VehicleTestDataBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class VehicleDataValidationTests {

    private ParkingValidation sut;
    private Vehicle vehicle;
    private VehicleTestDataBuilder vehicleTestDataBuilder;

    public VehicleDataValidationTests() {
        vehicleTestDataBuilder = new VehicleTestDataBuilder();
    }

    @Before
    public void setUp() {
        vehicle = vehicleTestDataBuilder.build();
        sut = new VehicleDataValidation();
    }

    @After
    public void tearDown() {
        sut = null;
        vehicle = null;
    }

    @Test
    public void validateWithoutException() throws BaseException {
        sut.execute(Optional.ofNullable(vehicle));
    }

    @Test(expected = VehicleDataException.class)
    public void validateWithoutVehicle() throws BaseException {
        sut.execute(Optional.empty());
    }

    @Test(expected = VehicleDataException.class)
    public void validateWithoutLicensePlate() throws BaseException {
        vehicle = vehicleTestDataBuilder.withLicensePlate(null).build();
        sut.execute(Optional.ofNullable(vehicle));
    }

    @Test(expected = VehicleDataException.class)
    public void validateWithoutVehicleType() throws BaseException {
        vehicle = vehicleTestDataBuilder.withVehicleType(null).build();
        sut.execute(Optional.ofNullable(vehicle));
    }
}
