package co.com.ceiba.parkinglotbackend.core.repositories;

import co.com.ceiba.parkinglotbackend.core.entities.VehicleType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VehicleTypeRepository extends CrudRepository<VehicleType, Integer> {

    /**
     * Get a vehicle type by name
     *
     * @param name vehicle type name
     * @return requested vehicle type
     */
    Optional<VehicleType> findByName(String name);

}
