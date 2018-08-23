package co.com.ceiba.parkinglotbackend.exceptions;

public class VehicleDoesNotExistException extends Exception {

    public VehicleDoesNotExistException() {
        super("Vehicle does not exist, check license plate");
    }
}
