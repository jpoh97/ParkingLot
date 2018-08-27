package co.com.ceiba.parkinglotbackend.utils;

import co.com.ceiba.parkinglotbackend.core.entities.ParkingRates;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.exceptions.InvalidDatesException;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ParkingCalculatorUtil {

    public static Long calculatePrice(ParkingRates parkingRates, LocalDateTime entryDate, LocalDateTime departureDate)
            throws InvalidDatesException {
        Long finalPrice = 0L;
        ParkingCalendarUtil.DifferenceTimes differenceTimes = ParkingCalendarUtil.getDifferenceTime(entryDate, departureDate);
        if (differenceTimes.getDays() < 0 || differenceTimes.getHours() < 0) {
            throw new InvalidDatesException();
        }

        finalPrice = differenceTimes.getDays() * parkingRates.getDayPrice();
        finalPrice += differenceTimes.getHours() * parkingRates.getHourPrice();

        if(parkingRates.getExtraPrice() != null) {
            finalPrice += parkingRates.getExtraPrice();
        }

        return finalPrice;
    }
}
