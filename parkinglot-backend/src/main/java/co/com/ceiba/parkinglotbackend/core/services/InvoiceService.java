package co.com.ceiba.parkinglotbackend.core.services;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;

import java.util.Optional;
import java.util.stream.Stream;

public interface InvoiceService {

    Invoice save(Invoice invoice);

    Optional<Invoice> getVehicleInParking(String licensePlate);

    Stream<Invoice> getParkingSpacesInUseForVehicleType(Integer vehicleTypeId);

    Long getParkingSpacesCountInUseForVehicleType(Integer vehicleTypeId);
}
