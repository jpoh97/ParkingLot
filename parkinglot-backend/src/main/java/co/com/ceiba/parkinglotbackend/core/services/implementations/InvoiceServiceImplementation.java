package co.com.ceiba.parkinglotbackend.core.services.implementations;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.core.entities.ParkingRates;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.core.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.exceptions.*;
import co.com.ceiba.parkinglotbackend.core.repositories.InvoiceRepository;
import co.com.ceiba.parkinglotbackend.core.services.interfaces.InvoiceService;
import co.com.ceiba.parkinglotbackend.core.services.interfaces.ParkingAttendantService;
import co.com.ceiba.parkinglotbackend.utils.ParkingCalendarUtil;

import java.time.LocalDate;
import java.util.Optional;

public class InvoiceServiceImplementation implements InvoiceService {

    private ParkingAttendantService parkingAttendantService;
    private ParkingCalendarUtil parkingCalendarUtil;
    private InvoiceRepository invoiceRepository;
    private Optional<Invoice> recoveredInvoice;
    private Invoice invoice;

    public InvoiceServiceImplementation(ParkingAttendantService parkingAttendantService,
                                        InvoiceRepository invoiceRepository,
                                        ParkingCalendarUtil parkingCalendarUtil) {
        this.parkingAttendantService = parkingAttendantService;
        this.parkingCalendarUtil = parkingCalendarUtil;
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice create(Optional<Vehicle> vehicle) throws VehicleDataException,
            NoPlacesAvailableException, InvalidDayLicensePlateException {
        if (!vehicle.isPresent()
                || vehicle.get().getLicensePlate() == null
                || vehicle.get().getVehicleType() == null) {
            throw new VehicleDataException();
        }

        if ((vehicle.get().getVehicleType() == VehicleType.CAR
                && !parkingAttendantService.isThereParkingCarPlacesAvailable())
                || (vehicle.get().getVehicleType() == VehicleType.MOTORCYCLE
                && !parkingAttendantService.isThereParkingMotorcyclePlacesAvailable())) {
            throw new NoPlacesAvailableException();
        }

        if (!parkingAttendantService.isLicensePlateEnabledToEnter(vehicle.get().getLicensePlate())) {
            throw new InvalidDayLicensePlateException();
        }

        invoice = new Invoice(vehicle.get(), LocalDate.now());
        invoiceRepository.save(invoice);

        return invoice;
    }

    public Invoice generate(String licensePlate) throws VehicleDoesNotExistException, InvalidDatesException {
        recoveredInvoice = invoiceRepository.findByVehicleLicensePlateAndDepartureDateIsNull(licensePlate);
        if (!recoveredInvoice.isPresent()) {
            throw new VehicleDoesNotExistException();
        }
        invoice = recoveredInvoice.get();

        try {
            Long price = calculatePrice(invoice.getVehicle().getVehicleType(), invoice.getEntryDate(), LocalDate.now());
        } catch (InvalidDatesException e) {
            throw new InvalidDatesException();
        }
        return invoice;
    }

    private Long calculatePrice(VehicleType vehicleType, LocalDate entryDate, LocalDate departureDate) throws InvalidDatesException {
        Long finalPrice = 0L;
        ParkingCalendarUtil.DifferenceTimes differenceTimes = parkingCalendarUtil.getDifferenceTime(entryDate, departureDate);
        if (differenceTimes.getDays() < 0 || differenceTimes.getHours() < 0) {
            throw new InvalidDatesException();
        }
        if (vehicleType == VehicleType.CAR) {
            finalPrice += differenceTimes.getDays() * ParkingRates.CAR_DAY_PRICE.getValue();
            finalPrice += differenceTimes.getHours() * ParkingRates.CAR_HOUR_PRICE.getValue();
        } else {
            finalPrice += differenceTimes.getDays() * ParkingRates.MOTORCYCLE_DAY_PRICE.getValue();
            finalPrice += differenceTimes.getHours() * ParkingRates.MOTORCYCLE_HOUR_PRICE.getValue();
        }
        return finalPrice;
    }
}
