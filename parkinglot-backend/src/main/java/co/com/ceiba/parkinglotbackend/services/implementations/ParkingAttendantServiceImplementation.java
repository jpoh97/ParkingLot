package co.com.ceiba.parkinglotbackend.services.implementations;

import co.com.ceiba.parkinglotbackend.entities.Invoice;
import co.com.ceiba.parkinglotbackend.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.repositories.InvoiceRepository;
import co.com.ceiba.parkinglotbackend.services.interfaces.ParkingAttendantService;
import co.com.ceiba.parkinglotbackend.services.interfaces.ParkingCalendarService;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class ParkingAttendantServiceImplementation implements ParkingAttendantService {

    private ParkingCalendarService parkingCalendarService;
    private InvoiceRepository invoiceRepository;

    private Stream<Invoice> carsInParking;
    private Stream<Invoice> motorcyclesInParking;

    public ParkingAttendantServiceImplementation(ParkingCalendarService parkingCalendarService, InvoiceRepository invoiceRepository) {
        this.parkingCalendarService = parkingCalendarService;
        this.invoiceRepository = invoiceRepository;
    }

    public Boolean isLicensePlateStartingWithA(String licensePlate) {
        return licensePlate.startsWith("A");
    }

    public Boolean isLicensePlateEnabledToEnter(String licensePlate) {
        if(isLicensePlateStartingWithA(licensePlate)) {
            return parkingCalendarService.isSundayOrMonday();
        }
        return true;
    }

    public Stream<Invoice> getParkingCarPlacesInUse() {
        carsInParking = invoiceRepository.findAllByVehicleTypeAndDepartureDateIsNull(VehicleType.CAR);
        return carsInParking;
    }

    public Stream<Invoice> getParkingMotorcyclePlacesInUse() {
        motorcyclesInParking = invoiceRepository.findAllByVehicleTypeAndDepartureDateIsNull(VehicleType.MOTORCYCLE);
        return carsInParking;
    }

    public Boolean isThereParkingCarPlacesAvailable() {
        return getParkingCarPlacesInUse().count() < 20;
    }

    public Boolean isThereParkingMotorcyclePlacesAvailable() {
        return getParkingMotorcyclePlacesInUse().count() < 10;
    }
}
