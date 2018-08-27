package co.com.ceiba.parkinglotbackend.core.services.implementations;

import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.core.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.core.services.VehicleTypeService;
import co.com.ceiba.parkinglotbackend.exceptions.VehicleDataException;
import co.com.ceiba.parkinglotbackend.exceptions.VehicleDoesNotExistException;
import co.com.ceiba.parkinglotbackend.core.repositories.VehicleRepository;
import co.com.ceiba.parkinglotbackend.core.services.VehicleService;
import co.com.ceiba.parkinglotbackend.utils.VehicleTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        Optional<Vehicle> vehicle = vehicleRepository.findByLicensePlate(licensePlate.get());
        if (!vehicle.isPresent()) {
            throw new VehicleDoesNotExistException();
        }
        return vehicle;
    }

    public Optional<Vehicle> add(Optional<Vehicle> newVehicle) throws VehicleDataException {
        if (!newVehicle.isPresent() || newVehicle.get().getLicensePlate() == null) {
            throw new VehicleDataException();
        }
        Optional<Vehicle> vehicle = vehicleRepository.findByLicensePlate(newVehicle.get().getLicensePlate());
        if (vehicle.isPresent() && vehicle.get().getCylinderCapacity() != newVehicle.get().getCylinderCapacity()
                && newVehicle.get().getCylinderCapacity().isPresent()) {
            vehicle.get().setCylinderCapacity(newVehicle.get().getCylinderCapacity().get());
            vehicleRepository.save(vehicle.get());
        }
        return newVehicle;
    }

    public Vehicle getNewVehicle(String licensePlate, VehicleTypeEnum vehicleTypeEnum, Integer cylinderCapacity)
            throws VehicleDataException {
        Optional<VehicleType> vehicleType = vehicleTypeService.getCurrentVehicleType(vehicleTypeEnum.getValue());
        if (!vehicleType.isPresent()) {
            throw new VehicleDataException();
        }
        Vehicle vehicle = new Vehicle(licensePlate, cylinderCapacity, vehicleType.get(), LocalDateTime.now());
        return vehicle;
    }
}