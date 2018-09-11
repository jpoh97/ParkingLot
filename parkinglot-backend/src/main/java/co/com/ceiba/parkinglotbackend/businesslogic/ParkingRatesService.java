package co.com.ceiba.parkinglotbackend.businesslogic;

import co.com.ceiba.parkinglotbackend.persistence.entities.ParkingRates;
import co.com.ceiba.parkinglotbackend.persistence.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.ParkingRatesDataException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ParkingRatesService {

    Stream<ParkingRates> getAll();

    List<ParkingRates> getCurrentParkingRates(Boolean active) throws ParkingRatesDataException;

    ParkingRates getCurrentParkingRatesFor(VehicleType vehicleType, Optional<Integer> cylinderCapacity) throws ParkingRatesDataException;

}
