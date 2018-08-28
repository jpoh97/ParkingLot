package co.com.ceiba.parkinglotbackend.validations.implementations;

import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.exceptions.InvalidDayLicensePlateException;
import co.com.ceiba.parkinglotbackend.utils.ParkingCalendarUtil;
import co.com.ceiba.parkinglotbackend.validations.ParkingValidation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class InvalidDayLicensePlateValidation implements ParkingValidation {

    public void execute(Optional<Vehicle> vehicle) throws InvalidDayLicensePlateException {
        if (!isLicensePlateEnabledToEnter(vehicle.get().getLicensePlate())) {
            throw new InvalidDayLicensePlateException();
        }
    }

    private Boolean isLicensePlateStartingWithA(String licensePlate) {
        return licensePlate.startsWith("A");
    }

    private Boolean isLicensePlateEnabledToEnter(String licensePlate) {
        if (isLicensePlateStartingWithA(licensePlate)) {
            return ParkingCalendarUtil.isSundayOrMonday(LocalDate.now());
        }
        return true;
    }
}
