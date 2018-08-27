package co.com.ceiba.parkinglotbackend.core.services.implementations;

import co.com.ceiba.parkinglotbackend.core.entities.ParkingRates;
import co.com.ceiba.parkinglotbackend.core.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.core.repositories.ParkingRatesRepository;
import co.com.ceiba.parkinglotbackend.core.services.ParkingRatesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class ParkingRatesServiceImplementation implements ParkingRatesService {

    private ParkingRatesRepository parkingRatesRepository;

    public ParkingRatesServiceImplementation(ParkingRatesRepository parkingRatesRepository) {
        this.parkingRatesRepository = parkingRatesRepository;
    }

    public Stream<ParkingRates> getAll() {
        return StreamSupport.stream(parkingRatesRepository.findAll().spliterator(), true);
    }

    public List<ParkingRates> getCurrentParkingRates(Boolean active) {
        return parkingRatesRepository.findAllByActive(active);
    }

    public ParkingRates getCurrentParkingRatesFor(VehicleType vehicleType) {
        List<ParkingRates> activeParkingRates = getCurrentParkingRates(true);
        for (ParkingRates parkingRates : activeParkingRates) {
            if (vehicleType.getId() == parkingRates.getVehicleType().getId()) {
                return parkingRates;
            }
        }
        return null;
    }
}
