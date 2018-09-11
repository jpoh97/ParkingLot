package co.com.ceiba.parkinglotbackend.businesslogic.parkingattendant;

import co.com.ceiba.parkinglotbackend.persistence.entities.Invoice;
import co.com.ceiba.parkinglotbackend.persistence.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.exceptions.BaseException;

public interface ParkingAttendant {

    Invoice vehicleCheckIn(Vehicle vehicleResponse) throws BaseException;

    Invoice vehicleCheckOut(String licensePlate) throws BaseException;

}
