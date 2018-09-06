package co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.implementations;

import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.ParkingCalendarUtil;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.ParkingValidation;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvalidDayLicensePlateException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InvalidDayLicensePlateValidation implements ParkingValidation {

    private final ParkingCalendarUtil parkingCalendarUtil;

    public InvalidDayLicensePlateValidation(ParkingCalendarUtil parkingCalendarUtil) {
        this.parkingCalendarUtil = parkingCalendarUtil;
    }

    public void execute(Optional<Vehicle> vehicle) throws InvalidDayLicensePlateException {
        if (vehicle.isPresent()) {
            String licensePlate = vehicle.get().getLicensePlate();
            if (!Optional.ofNullable(licensePlate).isPresent() || !isLicensePlateEnabledToEnter(licensePlate)) {
                throw new InvalidDayLicensePlateException();
            }
        }
    }

    private Boolean isLicensePlateStartingWithA(String licensePlate) {
        return licensePlate.startsWith("A") || licensePlate.startsWith("a");
    }

    private Boolean isLicensePlateEnabledToEnter(String licensePlate) {
        if (isLicensePlateStartingWithA(licensePlate)) {
            return parkingCalendarUtil.isSundayOrMondayToday();
        }
        return true;
    }
}
