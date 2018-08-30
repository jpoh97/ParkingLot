package co.com.ceiba.parkinglotbackend.exceptions.implementations;

import co.com.ceiba.parkinglotbackend.exceptions.BaseException;

public class VehicleDataException extends BaseException {

    public VehicleDataException() {
        super("Vehicle data is wrong");
    }
}
