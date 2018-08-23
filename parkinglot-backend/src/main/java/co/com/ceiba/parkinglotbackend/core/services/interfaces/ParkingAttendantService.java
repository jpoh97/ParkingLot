package co.com.ceiba.parkinglotbackend.core.services.interfaces;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;

import java.util.stream.Stream;

public interface ParkingAttendantService {

    Boolean isLicensePlateEnabledToEnter(String licensePlate);

    Stream<Invoice> getParkingCarPlacesInUse();

    Stream<Invoice> getParkingMotorcyclePlacesInUse();

    Boolean isThereParkingCarPlacesAvailable();

    Boolean isThereParkingMotorcyclePlacesAvailable();

}
