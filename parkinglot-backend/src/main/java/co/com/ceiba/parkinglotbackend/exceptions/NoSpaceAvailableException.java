package co.com.ceiba.parkinglotbackend.exceptions;

public class NoSpaceAvailableException extends Exception {

    public NoSpaceAvailableException() {
        super("No places available");
    }
}
