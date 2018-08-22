package co.com.ceiba.parkinglotbackend.services.interfaces;

import co.com.ceiba.parkinglotbackend.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.exceptions.VehicleDataErrorException;
import co.com.ceiba.parkinglotbackend.exceptions.VehicleLicensePlateInvalidException;

import java.util.Optional;

public interface VehicleService {

    Optional<Vehicle> getVehicle(Optional<String> licensePlate) throws VehicleLicensePlateInvalidException;

    Optional<Vehicle> addVehicle(Optional<Vehicle> newVehicle) throws VehicleDataErrorException;

    Boolean isCylinderCapacityGreaterThanFiveHundredAndIsAMotorcycle(Vehicle vehicle);

    Boolean isCylinderCapacityGreaterThanFiveHundred(Vehicle vehicle);

    Boolean isAMotorcycle(Vehicle vehicle);

}
