package co.com.ceiba.parkinglotbackend.core.services.implementations;

import co.com.ceiba.parkinglotbackend.core.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.core.repositories.VehicleTypeRepository;
import co.com.ceiba.parkinglotbackend.core.services.VehicleTypeService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleTypeServiceImplementation implements VehicleTypeService {

    private VehicleTypeRepository vehicleTypeRepository;

    public VehicleTypeServiceImplementation(VehicleTypeRepository vehicleTypeRepository) {
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    public Optional<VehicleType> getCurrentVehicleType(String name) {
        return vehicleTypeRepository.findByName(name);
    }
}
