package co.com.ceiba.parkinglotbackend.validations.implementations;

import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.exceptions.Implementations.VehicleDataException;
import co.com.ceiba.parkinglotbackend.validations.ParkingValidation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VehicleDataValidation implements ParkingValidation {

    public void execute(Optional<Vehicle> vehicle) throws VehicleDataException {
        if (!vehicle.isPresent()
                || null == vehicle.get().getLicensePlate()
                || null == vehicle.get().getVehicleType()) {
            throw new VehicleDataException();
        }
    }
}
