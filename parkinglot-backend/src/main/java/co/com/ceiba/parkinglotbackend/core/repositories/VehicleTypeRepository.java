package co.com.ceiba.parkinglotbackend.core.repositories;

import co.com.ceiba.parkinglotbackend.core.entities.VehicleType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VehicleTypeRepository extends CrudRepository<VehicleType, Integer> {

    Optional<VehicleType> findByName(String name);

}
