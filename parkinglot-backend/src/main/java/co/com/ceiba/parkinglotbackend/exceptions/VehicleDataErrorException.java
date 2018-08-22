package co.com.ceiba.parkinglotbackend.exceptions;

public class VehicleDataErrorException extends Exception {

    public VehicleDataErrorException() {
        super("Vehicle data is wrong");
    }
}
