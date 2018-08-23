package co.com.ceiba.parkinglotbackend.exceptions;

public class NoPlacesAvailableException extends Exception {

    public NoPlacesAvailableException() {
        super("No places available");
    }
}
