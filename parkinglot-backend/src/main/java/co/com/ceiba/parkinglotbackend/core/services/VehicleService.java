package co.com.ceiba.parkinglotbackend.core.services;

import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.exceptions.VehicleDataException;
import co.com.ceiba.parkinglotbackend.exceptions.VehicleDoesNotExistException;
import co.com.ceiba.parkinglotbackend.utils.VehicleTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VehicleService {

    Page<Vehicle> getAll(Pageable pageable);

    Optional<Vehicle> get(Optional<String> licensePlate) throws VehicleDoesNotExistException;

    Vehicle getNewVehicle(String licensePlate, VehicleTypeEnum vehicleTypeEnum, Integer cylinderCapacity) throws VehicleDataException;

    Optional<Vehicle> add(Optional<Vehicle> newVehicle) throws VehicleDataException;

}
