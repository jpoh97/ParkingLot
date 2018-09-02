package co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations;

import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.implementations.NoSpaceAvailableValidation;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.implementations.VehicleAlreadyExistsInParkingLotValidation;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.implementations.VehicleDataValidation;
import org.springframework.stereotype.Component;

@Component
public class ParkingValidationFactory {

    private NoSpaceAvailableValidation noSpaceAvailableValidation;
    private VehicleDataValidation vehicleDataValidation;
    private VehicleAlreadyExistsInParkingLotValidation vehicleAlreadyExistsInParkingLotValidation;

    public ParkingValidationFactory(NoSpaceAvailableValidation noSpaceAvailableValidation,
                                    VehicleDataValidation vehicleDataValidation,
                                    VehicleAlreadyExistsInParkingLotValidation vehicleAlreadyExistsInParkingLotValidation) {
        this.noSpaceAvailableValidation = noSpaceAvailableValidation;
        this.vehicleDataValidation = vehicleDataValidation;
        this.vehicleAlreadyExistsInParkingLotValidation = vehicleAlreadyExistsInParkingLotValidation;
    }

    public ParkingValidation getParkingValidation(ParkingValidationType parkingValidationType) {
        switch (parkingValidationType) {
            case VEHICLE_DATA:
                return vehicleDataValidation;
            case NO_SPACE_AVAILABLE:
                return noSpaceAvailableValidation;
            case VEHICLE_ALREADY_EXISTS_IN_PARKING_LOT:
                return vehicleAlreadyExistsInParkingLotValidation;
        }
        return null;
    }

    public enum ParkingValidationType {
        NO_SPACE_AVAILABLE,
        VEHICLE_DATA,
        VEHICLE_ALREADY_EXISTS_IN_PARKING_LOT
    }
}
