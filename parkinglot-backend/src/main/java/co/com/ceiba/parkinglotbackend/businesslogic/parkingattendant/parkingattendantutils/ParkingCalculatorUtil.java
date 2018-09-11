package co.com.ceiba.parkinglotbackend.businesslogic.parkingattendant.parkingattendantutils;

import co.com.ceiba.parkinglotbackend.persistence.entities.ParkingRates;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvalidDatesException;

import java.time.LocalDateTime;

public interface ParkingCalculatorUtil {

    Long calculatePrice(ParkingRates parkingRates, LocalDateTime entryDate) throws InvalidDatesException;

}
