package co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations;

import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.exceptions.BaseException;

import java.util.Optional;

public interface ParkingValidation {

    void execute(Optional<Vehicle> vehicle) throws BaseException;

}
