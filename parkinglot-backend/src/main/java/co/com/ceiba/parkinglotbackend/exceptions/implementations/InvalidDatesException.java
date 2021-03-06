package co.com.ceiba.parkinglotbackend.exceptions.implementations;

import co.com.ceiba.parkinglotbackend.exceptions.BaseException;

public class InvalidDatesException extends BaseException {

    public InvalidDatesException() {
        super("Invalid dates, departure date must be greater than entry date");
    }
}
