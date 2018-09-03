package co.com.ceiba.parkinglotbackend.core.services;

import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.exceptions.BaseException;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleDataException;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleDoesNotExistException;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleTypeDataException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VehicleService {

    Page<Vehicle> getAll(Pageable pageable) throws VehicleDataException;

    Optional<Vehicle> get(Optional<String> licensePlate) throws VehicleDoesNotExistException;

    Vehicle getNewVehicle(String licensePlate, String vehicleTypeString, Integer cylinderCapacity) throws BaseException;

    Optional<Vehicle> add(Optional<Vehicle> newVehicle) throws VehicleDataException;

}
