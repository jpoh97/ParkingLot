package co.com.ceiba.parkinglotbackend.core.repositories;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.core.entities.VehicleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface InvoiceRepository extends CrudRepository<Invoice, Integer>, PagingAndSortingRepository<Invoice, Integer> {

    Page<Stream<Invoice>> findByVehicleLicensePlate(String licensePlate, Pageable pageable);

    Stream<Invoice> findAllByDepartureDateIsNull(Pageable pageable);

    Stream<Invoice> findAllByVehicleVehicleTypeAndDepartureDateIsNull(VehicleType vehicleType);

    Optional<Invoice> findByVehicleLicensePlateAndDepartureDateIsNull(String licensePlate);

}