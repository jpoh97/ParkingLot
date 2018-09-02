package co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations;

import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvalidDayLicensePlateException;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.ParkingCalendarUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class InvalidDayLicensePlateValidation {

    public void execute(String licensePlate, LocalDateTime entryDate) throws InvalidDayLicensePlateException {
        if (!Optional.ofNullable(licensePlate).isPresent() || !isLicensePlateEnabledToEnter(licensePlate, entryDate)) {
            throw new InvalidDayLicensePlateException();
        }
    }

    private Boolean isLicensePlateStartingWithA(String licensePlate) {
        return licensePlate.startsWith("A") || licensePlate.startsWith("a");
    }

    private Boolean isLicensePlateEnabledToEnter(String licensePlate, LocalDateTime entryDate) throws InvalidDayLicensePlateException {
        if (isLicensePlateStartingWithA(licensePlate)) {
            return ParkingCalendarUtil.isSundayOrMonday(entryDate);
        }
        return true;
    }

}
