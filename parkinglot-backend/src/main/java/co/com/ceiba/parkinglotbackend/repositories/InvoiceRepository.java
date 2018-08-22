package co.com.ceiba.parkinglotbackend.repositories;

import co.com.ceiba.parkinglotbackend.entities.Invoice;
import co.com.ceiba.parkinglotbackend.entities.VehicleType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface InvoiceRepository extends CrudRepository<Invoice, Integer> {

    Optional<Invoice> findByVehicleLicensePlate(String licensePlate);

    Stream<Invoice> findAllByDepartureDateIsNull();

    Stream<Invoice> findAllByVehicleTypeAndDepartureDateIsNull(VehicleType vehicleType);

} 
