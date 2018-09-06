package co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils;

import co.com.ceiba.parkinglotbackend.core.entities.ParkingRates;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvalidDatesException;

import java.time.LocalDateTime;

public interface ParkingCalculatorUtil {

    Long calculatePrice(ParkingRates parkingRates, LocalDateTime entryDate) throws InvalidDatesException;

}
