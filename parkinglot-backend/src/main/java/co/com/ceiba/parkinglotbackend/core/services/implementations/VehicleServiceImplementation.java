package co.com.ceiba.parkinglotbackend.core.services.implementations;

import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.core.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.exceptions.VehicleDataException;
import co.com.ceiba.parkinglotbackend.exceptions.VehicleDoesNotExistException;
import co.com.ceiba.parkinglotbackend.core.repositories.VehicleRepository;
import co.com.ceiba.parkinglotbackend.core.services.interfaces.VehicleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleServiceImplementation implements VehicleService {

    private VehicleRepository vehicleRepository;
    private Optional<Vehicle> vehicle;

    public VehicleServiceImplementation(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Optional<Vehicle> getVehicle(Optional<String> licensePlate) throws VehicleDoesNotExistException {
        if (!licensePlate.isPresent()) {
            throw new VehicleDoesNotExistException();
        }
        vehicle = vehicleRepository.findByLicensePlate(licensePlate.get());
        if (!vehicle.isPresent()) {
            throw new VehicleDoesNotExistException();
        }
        return vehicle;
    }

    public Optional<Vehicle> addVehicle(Optional<Vehicle> newVehicle) throws VehicleDataException {
        if (!newVehicle.isPresent() || newVehicle.get().getLicensePlate() == null) {
            throw new VehicleDataException();
        }
        vehicle = vehicleRepository.findByLicensePlate(newVehicle.get().getLicensePlate());
        if (vehicle.isPresent() && vehicle.get().getCylinderCapacity() != newVehicle.get().getCylinderCapacity()) {
            vehicle.get().setCylinderCapacity(newVehicle.get().getCylinderCapacity());
            vehicleRepository.save(vehicle.get());
        }
        return newVehicle;
    }

    public Boolean isCylinderCapacityGreaterThanFiveHundredAndIsAMotorcycle(Vehicle vehicle) {
        return isAMotorcycle(vehicle) && isCylinderCapacityGreaterThanFiveHundred(vehicle);
    }

    private Boolean isCylinderCapacityGreaterThanFiveHundred(Vehicle vehicle) {
        if (!vehicle.getCylinderCapacity().isPresent()) {
            return false;
        }
        return vehicle.getCylinderCapacity().get() > 500;
    }

    private Boolean isAMotorcycle(Vehicle vehicle) {
        return vehicle.getVehicleType() == VehicleType.MOTORCYCLE;
    }
}