package co.com.ceiba.parkinglotbackend.exceptions;

public class NoSpaceAvailableException extends BaseException {

    public NoSpaceAvailableException() {
        super("No places available");
    }
}
