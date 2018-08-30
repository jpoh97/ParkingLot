package co.com.ceiba.parkinglotbackend.exceptions.implementations;

import co.com.ceiba.parkinglotbackend.exceptions.BaseException;

public class InvalidDayLicensePlateException extends BaseException {

    public InvalidDayLicensePlateException() {
        super("The license plates that start with 'A' can only enter on Sunday or Monday");
    }
}
