package co.com.ceiba.parkinglotbackend.businesslogic;

import co.com.ceiba.parkinglotbackend.persistence.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleTypeDataException;

import java.util.Optional;

public interface VehicleTypeService {

    Optional<VehicleType> getCurrentVehicleType(String name) throws VehicleTypeDataException;

}
