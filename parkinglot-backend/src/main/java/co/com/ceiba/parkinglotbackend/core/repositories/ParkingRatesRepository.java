package co.com.ceiba.parkinglotbackend.core.repositories;

import co.com.ceiba.parkinglotbackend.core.entities.ParkingRates;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParkingRatesRepository extends CrudRepository<ParkingRates, Integer> {

    /**
     * Find all active parking rates
     * @param active active or not active
     * @return list of parking rates
     */
    List<ParkingRates> findAllByActive(Boolean active);

}
