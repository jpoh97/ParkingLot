package co.com.ceiba.parkinglotbackend.core.services;

import co.com.ceiba.parkinglotbackend.core.entities.ParkingRates;
import co.com.ceiba.parkinglotbackend.core.entities.VehicleType;

import java.util.List;

public interface ParkingRatesService {

    List<ParkingRates> getCurrentParkingRates(Boolean active);

    ParkingRates getCurrentParkingRatesFor(VehicleType vehicleType);

}
