package co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils;

import co.com.ceiba.parkinglotbackend.core.entities.ParkingRates;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvalidDatesException;

import java.time.LocalDateTime;

public class ParkingCalculatorUtil {

    private ParkingCalculatorUtil() {}

    public static Long calculatePrice(ParkingRates parkingRates, LocalDateTime entryDate, LocalDateTime departureDate)
            throws InvalidDatesException {
        Long finalPrice = 0L;
        ParkingCalendarUtil.DifferenceTimes differenceTimes = ParkingCalendarUtil.getDifferenceTime(entryDate, departureDate);

        if (differenceTimes.getDays().equals(0L) && differenceTimes.getHours().equals(0L)) {
            return finalPrice;
        }

        finalPrice = differenceTimes.getDays() * parkingRates.getDayPrice();
        finalPrice += differenceTimes.getHours() * parkingRates.getHourPrice();

        if (null != parkingRates.getExtraPrice()) {
            finalPrice += parkingRates.getExtraPrice();
        }

        return finalPrice;
    }
}
