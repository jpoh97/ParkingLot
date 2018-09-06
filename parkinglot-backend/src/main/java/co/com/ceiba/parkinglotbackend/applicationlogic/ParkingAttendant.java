package co.com.ceiba.parkinglotbackend.applicationlogic;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.exceptions.BaseException;

public interface ParkingAttendant {

    Invoice vehicleCheckIn(Vehicle vehicleResponse) throws BaseException;

    Invoice vehicleCheckOut(String licensePlate) throws BaseException;

}
