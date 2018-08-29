package co.com.ceiba.parkinglotbackend.validations;

import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.exceptions.Implementations.InvalidDayLicensePlateException;
import co.com.ceiba.parkinglotbackend.utils.ParkingCalendarUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class InvalidDayLicensePlateValidation {

    public void execute(Optional<Vehicle> vehicle, LocalDateTime entryDate) throws InvalidDayLicensePlateException {
        if (!isLicensePlateEnabledToEnter(vehicle.get().getLicensePlate(), entryDate)) {
            throw new InvalidDayLicensePlateException();
        }
    }

    private Boolean isLicensePlateStartingWithA(String licensePlate) {
        return licensePlate.startsWith("A");
    }

    private Boolean isLicensePlateEnabledToEnter(String licensePlate, LocalDateTime entryDate) {
        if (isLicensePlateStartingWithA(licensePlate)) {
            return ParkingCalendarUtil.isSundayOrMonday(entryDate);
        }
        return true;
    }

}
