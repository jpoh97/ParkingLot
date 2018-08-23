package co.com.ceiba.parkinglotbackend.core.services.interfaces;

import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.exceptions.VehicleDataException;
import co.com.ceiba.parkinglotbackend.exceptions.VehicleDoesNotExistException;

import java.util.Optional;

public interface VehicleService {

    Optional<Vehicle> getVehicle(Optional<String> licensePlate) throws VehicleDoesNotExistException;

    Optional<Vehicle> addVehicle(Optional<Vehicle> newVehicle) throws VehicleDataException;

    Boolean isCylinderCapacityGreaterThanFiveHundredAndIsAMotorcycle(Vehicle vehicle);

}
