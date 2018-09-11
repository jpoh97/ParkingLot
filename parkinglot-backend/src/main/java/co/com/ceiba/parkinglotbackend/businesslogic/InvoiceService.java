package co.com.ceiba.parkinglotbackend.businesslogic;

import co.com.ceiba.parkinglotbackend.persistence.entities.Invoice;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvoiceDataException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.stream.Stream;

public interface InvoiceService {

    Invoice save(Invoice invoice) throws InvoiceDataException;

    Page<Invoice> getAll(Pageable pageable) throws InvoiceDataException;

    Page<Invoice> getAllInParking(Pageable pageable) throws InvoiceDataException;

    Optional<Invoice> getVehicleInParking(String licensePlate) throws InvoiceDataException;

    Stream<Invoice> getParkingSpacesInUseForVehicleType(String vehicleTypeName) throws InvoiceDataException;

    Long getParkingSpacesCountInUseForVehicleType(String vehicleTypeName) throws InvoiceDataException;

}
