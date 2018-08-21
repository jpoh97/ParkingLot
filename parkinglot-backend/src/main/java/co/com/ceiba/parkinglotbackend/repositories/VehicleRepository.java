package co.com.ceiba.parkinglotbackend.repositories;

import co.com.ceiba.parkinglotbackend.entities.Vehicle;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VehicleRepository extends CrudRepository<Vehicle, Integer> {

    Optional<Vehicle> findByLicensePlate(String licensePlate);

}
