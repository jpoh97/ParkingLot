package co.com.ceiba.parkinglotbackend.core.repositories;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface InvoiceRepository extends CrudRepository<Invoice, Integer>, PagingAndSortingRepository<Invoice, Integer> {

    /**
     * Find all invoices by a license plate
     *
     * @param licensePlate license plate of vehicle
     * @param pageable     page to retrieve
     * @return requested page
     */
    Page<Invoice> findByVehicleLicensePlate(String licensePlate, Pageable pageable);

    /**
     * Find all invoices of vehicles in parking lot
     *
     * @param pageable page to retrieve
     * @return requested page
     */
    Page<Invoice> findAllByDepartureDateIsNull(Pageable pageable);

    /**
     * Count all vehicles of a specific type in parking lot
     *
     * @param vehicleTypeName vehicle type name
     * @return quantity of vehicles
     */
    Long countByVehicleVehicleTypeNameAndDepartureDateIsNull(String vehicleTypeName);

    /**
     * Find all invoices of vehicles of a specific type in parking lot
     *
     * @param vehicleTypeName vehicle type name
     * @return stream of invoices
     */
    Stream<Invoice> findAllByVehicleVehicleTypeNameAndDepartureDateIsNull(String vehicleTypeName);

    /**
     * Find a vehicle in parking lot
     *
     * @param licensePlate license plate of vehicle
     * @return requested vehicle
     */
    Optional<Invoice> findByVehicleLicensePlateAndDepartureDateIsNull(String licensePlate);

}