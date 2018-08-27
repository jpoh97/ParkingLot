package co.com.ceiba.parkinglotbackend.applicationlogic;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.exceptions.InvalidDatesException;
import co.com.ceiba.parkinglotbackend.exceptions.VehicleDoesNotExistException;
import co.com.ceiba.parkinglotbackend.utils.VehicleTypeEnum;

import java.time.LocalDateTime;

public interface ParkingAttendant {

    Invoice vehicleCheckIn(String licensePlate, VehicleTypeEnum vehicleTypeEnum, Integer cylinderCapacity) throws Exception;

    Invoice vehicleCheckOut(String licensePlate, LocalDateTime departureDate) throws VehicleDoesNotExistException,
            InvalidDatesException;

}
