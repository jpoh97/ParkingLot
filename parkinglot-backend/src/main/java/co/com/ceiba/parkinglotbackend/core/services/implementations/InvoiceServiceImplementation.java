package co.com.ceiba.parkinglotbackend.core.services.implementations;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.core.repositories.InvoiceRepository;
import co.com.ceiba.parkinglotbackend.core.services.InvoiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class InvoiceServiceImplementation implements InvoiceService {

    private InvoiceRepository invoiceRepository;

    public InvoiceServiceImplementation(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Page<Invoice> getAll(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    public Page<Invoice> getAllInParking(Pageable pageable) {
        return invoiceRepository.findAllByDepartureDateIsNull(pageable);
    }

    public Stream<Invoice> getParkingSpacesInUseForVehicleType(String vehicleTypeName) {
        return invoiceRepository.findAllByVehicleVehicleTypeNameAndDepartureDateIsNull(vehicleTypeName);
    }

    public Long getParkingSpacesCountInUseForVehicleType(String vehicleTypeName) {
        return invoiceRepository.countByVehicleVehicleTypeNameAndDepartureDateIsNull(vehicleTypeName);
    }

    public Optional<Invoice> getVehicleInParking(String licensePlate) {
        return invoiceRepository.findByVehicleLicensePlateAndDepartureDateIsNull(licensePlate.trim().toUpperCase());
    }
}
