package co.com.ceiba.parkinglotbackend.core.services.implementations;

import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.core.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.core.services.VehicleTypeService;
import co.com.ceiba.parkinglotbackend.exceptions.Implementations.VehicleDataException;
import co.com.ceiba.parkinglotbackend.exceptions.Implementations.VehicleDoesNotExistException;
import co.com.ceiba.parkinglotbackend.core.repositories.VehicleRepository;
import co.com.ceiba.parkinglotbackend.core.services.VehicleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VehicleServiceImplementation implements VehicleService {

    private VehicleRepository vehicleRepository;
    private VehicleTypeService vehicleTypeService;

    public VehicleServiceImplementation(VehicleRepository vehicleRepository, VehicleTypeService vehicleTypeService) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleTypeService = vehicleTypeService;
    }

    public Page<Vehicle> getAll(Pageable pageable) {
        return vehicleRepository.findAll(pageable);
    }

    public Optional<Vehicle> get(Optional<String> licensePlate) throws VehicleDoesNotExistException {
        if (!licensePlate.isPresent()) {
            throw new VehicleDoesNotExistException();
        }
        Optional<Vehicle> vehicle = vehicleRepository.findByLicensePlate(licensePlate.get().trim().toUpperCase());
        if (!vehicle.isPresent()) {
            throw new VehicleDoesNotExistException();
        }
        return vehicle;
    }

    public Optional<Vehicle> add(Optional<Vehicle> newVehicle) throws VehicleDataException {
        if (!newVehicle.isPresent() || null == newVehicle.get().getLicensePlate() || null == newVehicle.get().getVehicleType()) {
            throw new VehicleDataException();
        }
        Optional<Vehicle> vehicle = vehicleRepository.findByLicensePlate(newVehicle.get().getLicensePlate());
        if (vehicle.isPresent()) {
            if(newVehicle.get().getCylinderCapacity().isPresent()
                    && !vehicle.get().getCylinderCapacity().get().equals(newVehicle.get().getCylinderCapacity().get())) {
                vehicle.get().setCylinderCapacity(newVehicle.get().getCylinderCapacity());
                vehicleRepository.save(vehicle.get());
            }
            return vehicle;
        }
        return newVehicle;
    }

    public Vehicle getNewVehicle(String licensePlate, String vehicleTypeString, Integer cylinderCapacity)
            throws VehicleDataException {
        Optional<VehicleType> vehicleType = vehicleTypeService.getCurrentVehicleType(vehicleTypeString.trim().toUpperCase());
        if (!vehicleType.isPresent()) {
            throw new VehicleDataException();
        }
        return new Vehicle(licensePlate.trim().toUpperCase(), cylinderCapacity, vehicleType.get(), LocalDateTime.now());
    }
}