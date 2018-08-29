package co.com.ceiba.parkinglotbackend.exceptions.Implementations;

import co.com.ceiba.parkinglotbackend.exceptions.BaseException;

public class NoSpaceAvailableException extends BaseException {

    public NoSpaceAvailableException() {
        super("No places available");
    }
}
