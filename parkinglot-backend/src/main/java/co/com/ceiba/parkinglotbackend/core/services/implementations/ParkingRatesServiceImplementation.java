package co.com.ceiba.parkinglotbackend.core.services.implementations;

import co.com.ceiba.parkinglotbackend.core.entities.ParkingRates;
import co.com.ceiba.parkinglotbackend.core.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.core.repositories.ParkingRatesRepository;
import co.com.ceiba.parkinglotbackend.core.services.ParkingRatesService;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.VehicleTypeEnum;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.ParkingRatesDataException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class ParkingRatesServiceImplementation implements ParkingRatesService {

    private ParkingRatesRepository parkingRatesRepository;

    // Constants
    private static final Long EXTRA_VALUE_FOR_MOTORCYCLE_WITH_CYLINDER_GREATER_THAN_500 = 2000L;
    private static final Long MAXIMUM_CYLINDER_WITHOUT_EXTRA_VALUE = 500L;

    public ParkingRatesServiceImplementation(ParkingRatesRepository parkingRatesRepository) {
        this.parkingRatesRepository = parkingRatesRepository;
    }

    public Stream<ParkingRates> getAll() {
        return StreamSupport.stream(parkingRatesRepository.findAll().spliterator(), true);
    }

    public List<ParkingRates> getCurrentParkingRates(Boolean active) throws ParkingRatesDataException {
        if (!Optional.ofNullable(active).isPresent()) {
            throw new ParkingRatesDataException();
        }
        return parkingRatesRepository.findAllByActive(active);
    }

    public ParkingRates getCurrentParkingRatesFor(VehicleType vehicleType, Optional<Integer> cylinderCapacity) throws ParkingRatesDataException {
        Long extraValue = getExtraPriceForMotorcycle(vehicleType, cylinderCapacity);
        List<ParkingRates> activeParkingRates = getCurrentParkingRates(true);
        for (ParkingRates parkingRates : activeParkingRates) {
            if (vehicleType.getId().equals(parkingRates.getVehicleType().getId())
                    && extraValue.equals(parkingRates.getExtraPrice())) {
                return parkingRates;
            }
        }
        return null;
    }

    private Long getExtraPriceForMotorcycle(VehicleType vehicleType, Optional<Integer> cylinderCapacity) throws ParkingRatesDataException {
        if (!Optional.ofNullable(vehicleType).isPresent() || !Optional.ofNullable(vehicleType.getName()).isPresent()
                || !cylinderCapacity.isPresent()) {
            throw new ParkingRatesDataException();
        }
        long extraValue = 0L;
        if (vehicleType.getName().equalsIgnoreCase(VehicleTypeEnum.MOTORCYCLE.getValue())) {
            extraValue = (cylinderCapacity.get() > MAXIMUM_CYLINDER_WITHOUT_EXTRA_VALUE) ?
                    EXTRA_VALUE_FOR_MOTORCYCLE_WITH_CYLINDER_GREATER_THAN_500 : 0;
        }
        return extraValue;
    }
}
