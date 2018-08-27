package co.com.ceiba.parkinglotbackend.core.repositories;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface InvoiceRepository extends CrudRepository<Invoice, Integer>, PagingAndSortingRepository<Invoice, Integer> {

    Page<Invoice> findByVehicleLicensePlate(String licensePlate, Pageable pageable);

    Stream<Invoice> findAllByDepartureDateIsNull(Pageable pageable);

    Long countByVehicleVehicleTypeIdAndDepartureDateIsNull(Integer vehicleTypeId);

    Stream<Invoice> findAllByVehicleVehicleTypeIdAndDepartureDateIsNull(Integer vehicleTypeId);

    Optional<Invoice> findByVehicleLicensePlateAndDepartureDateIsNull(String licensePlate);

}