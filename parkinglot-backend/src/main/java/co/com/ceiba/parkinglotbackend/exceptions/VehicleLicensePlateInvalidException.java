package co.com.ceiba.parkinglotbackend.exceptions;

public class VehicleLicensePlateInvalidException extends Exception {

    public VehicleLicensePlateInvalidException() {
        super("Vehicle does not exist, check license plate");
    }
}
