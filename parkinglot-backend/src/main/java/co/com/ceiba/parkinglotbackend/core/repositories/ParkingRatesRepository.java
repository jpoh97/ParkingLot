package co.com.ceiba.parkinglotbackend.core.repositories;

import co.com.ceiba.parkinglotbackend.core.entities.ParkingRates;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParkingRatesRepository extends CrudRepository<ParkingRates, Integer> {

    List<ParkingRates> findAllByActive(Boolean active);

}
