package co.com.ceiba.parkinglotbackend.services.implementations;

import co.com.ceiba.parkinglotbackend.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.exceptions.VehicleDataErrorException;
import co.com.ceiba.parkinglotbackend.exceptions.VehicleLicensePlateInvalidException;
import co.com.ceiba.parkinglotbackend.repositories.VehicleRepository;
import co.com.ceiba.parkinglotbackend.services.interfaces.VehicleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleServiceImplementation implements VehicleService {

    private VehicleRepository vehicleRepository;
    Optional<Vehicle> vehicle;

    public VehicleServiceImplementation(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Optional<Vehicle> getVehicle(Optional<String> licensePlate) throws VehicleLicensePlateInvalidException {
        if (!licensePlate.isPresent()) { throw new VehicleLicensePlateInvalidException(); }
        vehicle = vehicleRepository.findByLicensePlate(licensePlate.get());
        if (!vehicle.isPresent()) { throw new VehicleLicensePlateInvalidException(); }
        return vehicle;
    }

    public Optional<Vehicle> addVehicle(Optional<Vehicle> newVehicle) throws VehicleDataErrorException {
        if (!newVehicle.isPresent() || newVehicle.get().getLicensePlate() == null) { throw new VehicleDataErrorException(); }
        vehicle = vehicleRepository.findByLicensePlate(newVehicle.get().getLicensePlate());
        if(vehicle.isPresent() && vehicle.get().getCylinderCapacity() != newVehicle.get().getCylinderCapacity()) {
            vehicle.get().setCylinderCapacity(newVehicle.get().getCylinderCapacity());
            vehicleRepository.save(vehicle.get());
        }
        return newVehicle;
    }

    public Boolean isCylinderCapacityGreaterThanFiveHundredAndIsAMotorcycle(Vehicle vehicle)  {
        return isAMotorcycle(vehicle) && isCylinderCapacityGreaterThanFiveHundred(vehicle);
    }

    public Boolean isCylinderCapacityGreaterThanFiveHundred(Vehicle vehicle) {
        if (!vehicle.getCylinderCapacity().isPresent()) { return false; }
        return vehicle.getCylinderCapacity().get() > 500;
    }

    public Boolean isAMotorcycle(Vehicle vehicle) {
        return vehicle.getVehicleType() == VehicleType.MOTORCYCLE;
    }
}