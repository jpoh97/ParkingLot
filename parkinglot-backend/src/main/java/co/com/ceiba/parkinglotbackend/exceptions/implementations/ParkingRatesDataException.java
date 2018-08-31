package co.com.ceiba.parkinglotbackend.exceptions.implementations;

import co.com.ceiba.parkinglotbackend.exceptions.BaseException;

public class ParkingRatesDataException extends BaseException {
    public ParkingRatesDataException() {
        super("Parking rates data is wrong");
    }
}
