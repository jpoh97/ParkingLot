package co.com.ceiba.parkinglotbackend.core.services.implementations;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.core.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.core.repositories.InvoiceRepository;
import co.com.ceiba.parkinglotbackend.core.services.interfaces.ParkingAttendantService;
import co.com.ceiba.parkinglotbackend.utils.ParkingCalendarUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Stream;

@Service
public class ParkingAttendantServiceImplementation implements ParkingAttendantService {

    private InvoiceRepository invoiceRepository;

    private Stream<Invoice> carsInParking;
    private Stream<Invoice> motorcyclesInParking;

    public ParkingAttendantServiceImplementation(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    private Boolean isLicensePlateStartingWithA(String licensePlate) {
        return licensePlate.startsWith("A");
    }

    public Boolean isLicensePlateEnabledToEnter(String licensePlate) {
        if (isLicensePlateStartingWithA(licensePlate)) {
            return ParkingCalendarUtil.isSundayOrMonday(LocalDate.now());
        }
        return true;
    }

    public Stream<Invoice> getParkingCarPlacesInUse() {
        carsInParking = invoiceRepository.findAllByVehicleVehicleTypeAndDepartureDateIsNull(VehicleType.CAR);
        return carsInParking;
    }

    public Stream<Invoice> getParkingMotorcyclePlacesInUse() {
        motorcyclesInParking = invoiceRepository.findAllByVehicleVehicleTypeAndDepartureDateIsNull(VehicleType.MOTORCYCLE);
        return motorcyclesInParking;
    }

    public Boolean isThereParkingCarPlacesAvailable() {
        return getParkingCarPlacesInUse().count() < 20;
    }

    public Boolean isThereParkingMotorcyclePlacesAvailable() {
        return getParkingMotorcyclePlacesInUse().count() < 10;
    }
}
