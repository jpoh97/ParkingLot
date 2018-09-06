package co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.implementations;

import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.ParkingCalculatorUtil;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.ParkingCalendarUtil;
import co.com.ceiba.parkinglotbackend.core.entities.ParkingRates;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvalidDatesException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ParkingCalculatorUtilImplementation implements ParkingCalculatorUtil {

    private final ParkingCalendarUtil parkingCalendarUtil;

    public ParkingCalculatorUtilImplementation(ParkingCalendarUtil parkingCalendarUtil) {
        this.parkingCalendarUtil = parkingCalendarUtil;
    }

    public Long calculatePrice(ParkingRates parkingRates, LocalDateTime entryDate) throws InvalidDatesException {
        Long finalPrice = 0L;
        ParkingCalendarUtil.DifferenceTimes differenceTimes = parkingCalendarUtil.getDifferenceTime(entryDate, parkingCalendarUtil.getTodayDate());

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
