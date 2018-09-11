package co.com.ceiba.parkinglotbackend.businesslogic.parkingattendant.parkingattendantvalidations;

import co.com.ceiba.parkinglotbackend.persistence.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.exceptions.BaseException;

import java.util.Optional;

public interface ParkingValidation {

    void execute(Optional<Vehicle> vehicle) throws BaseException;

}
