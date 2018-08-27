package co.com.ceiba.parkinglotbackend.validations;

import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;

import java.util.Optional;

public interface ParkingValidation {
    void execute(Optional<Vehicle> vehicle) throws Exception;
}
