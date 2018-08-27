package co.com.ceiba.parkinglotbackend.validations;

import co.com.ceiba.parkinglotbackend.validations.implementations.InvalidDayLicensePlateValidation;
import co.com.ceiba.parkinglotbackend.validations.implementations.NoSpaceAvailableValidation;
import co.com.ceiba.parkinglotbackend.validations.implementations.VehicleDataValidation;

import java.util.function.Supplier;

public class ParkingValidationFactory {

    public static enum ParkingValidationType {
        NO_SPACE_AVAILABLE(NoSpaceAvailableValidation::new),
        INVALID_DAY_LICENSE_PLATE(InvalidDayLicensePlateValidation::new),
        VEHICLE_DATA_VALIDATION(VehicleDataValidation::new);

        private final Supplier<ParkingValidation> constructor;
        ParkingValidationType(Supplier<ParkingValidation> constructor) { this.constructor = constructor; }
        public Supplier<ParkingValidation> getConstructor() { return constructor; }
    }


    public static ParkingValidation getInstance(ParkingValidationType parkingValidationType) {
        return parkingValidationType.getConstructor().get();
    }

}
