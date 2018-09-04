package co.com.ceiba.parkinglotbackend.core.repositories;

import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface VehicleRepository extends CrudRepository<Vehicle, Integer>, PagingAndSortingRepository<Vehicle, Integer> {

    /**
     * Find all vehicles
     * @param pageable page to retrieve
     * @return requested page
     */
    Page<Vehicle> findAll(Pageable pageable);

    /**
     * Find a vehicle by license plate
     * @param licensePlate vehicle license plate
     * @return requested vehicle
     */
    Optional<Vehicle> findByLicensePlate(String licensePlate);

}