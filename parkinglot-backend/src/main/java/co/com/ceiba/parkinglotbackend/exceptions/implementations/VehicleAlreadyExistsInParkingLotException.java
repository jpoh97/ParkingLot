package co.com.ceiba.parkinglotbackend.exceptions.implementations;

import co.com.ceiba.parkinglotbackend.exceptions.BaseException;

public class VehicleAlreadyExistsInParkingLotException extends BaseException {

    public VehicleAlreadyExistsInParkingLotException() {
        super("Vehicle already exists in parking lot. Check license plate");
    }
}
