package co.com.ceiba.parkinglotbackend.core.services.implementations;

import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.core.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.core.services.VehicleTypeService;
import co.com.ceiba.parkinglotbackend.exceptions.BaseException;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleDataException;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleDoesNotExistException;
import co.com.ceiba.parkinglotbackend.core.repositories.VehicleRepository;
import co.com.ceiba.parkinglotbackend.core.services.VehicleService;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleTypeDataException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VehicleServiceImplementation implements VehicleService {

    private final ThreadLocal<VehicleRepository> vehicleRepository = new ThreadLocal<>();
    private final ThreadLocal<VehicleTypeService> vehicleTypeService = new ThreadLocal<>();

    public VehicleServiceImplementation(VehicleRepository vehicleRepository, VehicleTypeService vehicleTypeService) {
        this.vehicleRepository.set(vehicleRepository);
        this.vehicleTypeService.set(vehicleTypeService);
    }

    public Page<Vehicle> getAll(Pageable pageable) throws VehicleDataException {
        if (!Optional.ofNullable(pageable).isPresent()) {
            throw new VehicleDataException();
        }
        return vehicleRepository.get().findAll(pageable);
    }

    public Optional<Vehicle> get(Optional<String> licensePlate) throws VehicleDoesNotExistException {
        if (!licensePlate.isPresent()) {
            throw new VehicleDoesNotExistException();
        }
        Optional<Vehicle> vehicle = vehicleRepository.get().findByLicensePlate(licensePlate.get().trim().toUpperCase());
        if (!vehicle.isPresent()) {
            throw new VehicleDoesNotExistException();
        }
        return vehicle;
    }

    public Optional<Vehicle> add(Optional<Vehicle> newVehicle) throws VehicleDataException {
        if (!newVehicle.isPresent() || null == newVehicle.get().getLicensePlate() || null == newVehicle.get().getVehicleType()) {
            throw new VehicleDataException();
        }
        newVehicle.get().setLicensePlate(newVehicle.get().getLicensePlate().trim().toUpperCase());
        Optional<Vehicle> vehicle = vehicleRepository.get().findByLicensePlate(newVehicle.get().getLicensePlate());
        if (vehicle.isPresent()) {
            if (newVehicle.get().getCylinderCapacity().isPresent() && vehicle.get().getCylinderCapacity().isPresent()
                    && !newVehicle.get().getCylinderCapacity().get().equals(vehicle.get().getCylinderCapacity().get())) {
                vehicle.get().setCylinderCapacity(newVehicle.get().getCylinderCapacity());
                vehicle = Optional.ofNullable(vehicleRepository.get().save(vehicle.get()));
            }
            return vehicle;
        }
        vehicle = Optional.ofNullable(vehicleRepository.get().save(newVehicle.get()));
        return vehicle;
    }

    public Vehicle getNewVehicle(String licensePlate, String vehicleTypeString, Integer cylinderCapacity)
            throws BaseException {
        if (null == licensePlate || null == vehicleTypeString) {
            throw new VehicleDataException();
        }
        Optional<VehicleType> vehicleType = vehicleTypeService.get().getCurrentVehicleType(vehicleTypeString.trim().toUpperCase());
        if (!vehicleType.isPresent()) {
            throw new VehicleTypeDataException();
        }
        return new Vehicle(licensePlate.trim().toUpperCase(), cylinderCapacity, vehicleType.get(), LocalDateTime.now());
    }
}