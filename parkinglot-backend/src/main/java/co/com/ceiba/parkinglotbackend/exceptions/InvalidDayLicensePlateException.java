package co.com.ceiba.parkinglotbackend.exceptions;

public class InvalidDayLicensePlateException extends Exception {

    public InvalidDayLicensePlateException() {
        super("The license plates that start with 'A' can only enter on Sunday or Monday");
    }
}
