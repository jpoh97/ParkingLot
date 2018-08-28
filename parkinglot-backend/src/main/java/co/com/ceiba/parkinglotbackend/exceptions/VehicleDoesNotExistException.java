package co.com.ceiba.parkinglotbackend.exceptions;

public class VehicleDoesNotExistException extends BaseException {

    public VehicleDoesNotExistException() {
        super("Vehicle does not exist in Parking Lot, check license plate");
    }
}
