package co.com.ceiba.parkinglotbackend.unittest.applicationlogic.parkingattendantvalidations.implementations;

import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.implementations.VehicleDataValidation;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleDataException;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.VehicleTestDataBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class VehicleDataValidationTests {

    private VehicleDataValidation sut;
    private Vehicle vehicle;
    private VehicleTestDataBuilder vehicleTestDataBuilder;

    @Before
    public void setUp() {
        vehicleTestDataBuilder = new VehicleTestDataBuilder();
        vehicle = vehicleTestDataBuilder.build();
        sut = new VehicleDataValidation();
    }

    @After
    public void tearDown() {
        sut = null;
        vehicle = null;
        vehicleTestDataBuilder = null;
    }

    @Test
    public void validateWithoutException() throws VehicleDataException {
        sut.execute(Optional.ofNullable(vehicle));
    }

    @Test(expected = VehicleDataException.class)
    public void validateWithoutVehicle() throws VehicleDataException {
        sut.execute(Optional.ofNullable(null));
    }

    @Test(expected = VehicleDataException.class)
    public void validateWithoutLicensePlate() throws VehicleDataException {
        vehicle = vehicleTestDataBuilder.withLicensePlate(null).build();
        sut.execute(Optional.ofNullable(vehicle));
    }

    @Test(expected = VehicleDataException.class)
    public void validateWithoutVehicleType() throws VehicleDataException {
        vehicle = vehicleTestDataBuilder.withVehicleType(null).build();
        sut.execute(Optional.ofNullable(vehicle));
    }
}
