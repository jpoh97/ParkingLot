package co.com.ceiba.parkinglotbackend.businesslogic.implementations;

import co.com.ceiba.parkinglotbackend.businesslogic.VehicleTypeService;
import co.com.ceiba.parkinglotbackend.persistence.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.persistence.repositories.VehicleTypeRepository;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleTypeDataException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleTypeServiceImplementation implements VehicleTypeService {

    private VehicleTypeRepository vehicleTypeRepository;

    public VehicleTypeServiceImplementation(VehicleTypeRepository vehicleTypeRepository) {
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    public Optional<VehicleType> getCurrentVehicleType(String name) throws VehicleTypeDataException {
        if (!Optional.ofNullable(name).isPresent()) {
            throw new VehicleTypeDataException();
        }
        return vehicleTypeRepository.findByName(name);
    }
}
