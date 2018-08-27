package co.com.ceiba.parkinglotbackend.core.services.implementations;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.core.repositories.InvoiceRepository;
import co.com.ceiba.parkinglotbackend.core.services.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class InvoiceServiceImplementation implements InvoiceService {

    private InvoiceRepository invoiceRepository;

    public InvoiceServiceImplementation(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Stream<Invoice> getParkingSpacesInUseForVehicleType(Integer vehicleTypeId) {
        Stream<Invoice> vehiclesInParkingLot = invoiceRepository.findAllByVehicleVehicleTypeIdAndDepartureDateIsNull(vehicleTypeId);
        return vehiclesInParkingLot;
    }

    public Long getParkingSpacesCountInUseForVehicleType(Integer vehicleTypeId) {
        return invoiceRepository.countByVehicleVehicleTypeIdAndDepartureDateIsNull(vehicleTypeId);
    }

    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Optional<Invoice> getVehicleInParking(String licensePlate) {
        return invoiceRepository.findByVehicleLicensePlateAndDepartureDateIsNull(licensePlate);
    }
}
