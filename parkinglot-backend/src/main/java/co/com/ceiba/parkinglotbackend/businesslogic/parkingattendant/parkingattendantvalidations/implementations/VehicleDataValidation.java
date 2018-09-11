package co.com.ceiba.parkinglotbackend.businesslogic.parkingattendant.parkingattendantvalidations.implementations;

import co.com.ceiba.parkinglotbackend.businesslogic.parkingattendant.parkingattendantvalidations.ParkingValidation;
import co.com.ceiba.parkinglotbackend.persistence.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleDataException;
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
