package co.com.ceiba.parkinglotbackend.core.services;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.stream.Stream;

public interface InvoiceService {

    Invoice save(Invoice invoice);

    Page<Invoice> getAll(Pageable pageable);

    Page<Invoice> getAllInParking(Pageable pageable);

    Optional<Invoice> getVehicleInParking(String licensePlate);

    Stream<Invoice> getParkingSpacesInUseForVehicleType(String vehicleTypeName);

    Long getParkingSpacesCountInUseForVehicleType(String vehicleTypeName);

}
