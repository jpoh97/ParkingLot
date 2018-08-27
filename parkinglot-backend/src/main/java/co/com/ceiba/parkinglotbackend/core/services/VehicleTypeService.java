package co.com.ceiba.parkinglotbackend.core.services;

import co.com.ceiba.parkinglotbackend.core.entities.VehicleType;

import java.util.Optional;

public interface VehicleTypeService {

    Optional<VehicleType> getCurrentVehicleType(String name);

}
