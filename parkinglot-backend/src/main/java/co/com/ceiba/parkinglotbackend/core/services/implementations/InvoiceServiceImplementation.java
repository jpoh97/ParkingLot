package co.com.ceiba.parkinglotbackend.core.services.implementations;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.core.repositories.InvoiceRepository;
import co.com.ceiba.parkinglotbackend.core.services.InvoiceService;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvoiceDataException;
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

    public Invoice save(Invoice invoice) throws InvoiceDataException {
        if(!Optional.ofNullable(invoice).isPresent()) {
            throw new InvoiceDataException();
        }
        return invoiceRepository.save(invoice);
    }

    public Page<Invoice> getAll(Pageable pageable) throws InvoiceDataException {
        if(!Optional.ofNullable(pageable).isPresent()) {
            throw new InvoiceDataException();
        }
        return invoiceRepository.findAll(pageable);
    }

    public Page<Invoice> getAllInParking(Pageable pageable) throws InvoiceDataException {
        if(!Optional.ofNullable(pageable).isPresent()) {
            throw new InvoiceDataException();
        }
        return invoiceRepository.findAllByDepartureDateIsNull(pageable);
    }

    public Stream<Invoice> getParkingSpacesInUseForVehicleType(String vehicleTypeName) throws InvoiceDataException {
        if(!Optional.ofNullable(vehicleTypeName).isPresent()) {
            throw new InvoiceDataException();
        }
        return invoiceRepository.findAllByVehicleVehicleTypeNameAndDepartureDateIsNull(vehicleTypeName);
    }

    public Long getParkingSpacesCountInUseForVehicleType(String vehicleTypeName) throws InvoiceDataException {
        if(!Optional.ofNullable(vehicleTypeName).isPresent()) {
            throw new InvoiceDataException();
        }
        return invoiceRepository.countByVehicleVehicleTypeNameAndDepartureDateIsNull(vehicleTypeName);
    }

    public Optional<Invoice> getVehicleInParking(String licensePlate) throws InvoiceDataException {
        if(!Optional.ofNullable(licensePlate).isPresent()) {
            throw new InvoiceDataException();
        }
        return invoiceRepository.findByVehicleLicensePlateAndDepartureDateIsNull(licensePlate.trim().toUpperCase());
    }
}
