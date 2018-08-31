package co.com.ceiba.parkinglotbackend.exceptions.implementations;

import co.com.ceiba.parkinglotbackend.exceptions.BaseException;

public class VehicleTypeDataException extends BaseException {
    public VehicleTypeDataException() {
        super("Vehicle type data is wrong");
    }
}
