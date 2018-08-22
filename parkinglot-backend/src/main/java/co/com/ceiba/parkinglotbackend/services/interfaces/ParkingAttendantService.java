package co.com.ceiba.parkinglotbackend.services.interfaces;

import co.com.ceiba.parkinglotbackend.entities.Invoice;

import java.util.stream.Stream;

public interface ParkingAttendantService {

    Boolean isLicensePlateStartingWithA(String licensePlate);

    Boolean isLicensePlateEnabledToEnter(String licensePlate);

    Stream<Invoice> getParkingCarPlacesInUse();

    Stream<Invoice> getParkingMotorcyclePlacesInUse();

    Boolean isThereParkingCarPlacesAvailable();

    Boolean isThereParkingMotorcyclePlacesAvailable();

}
