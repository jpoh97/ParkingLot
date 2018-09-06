package co.com.ceiba.parkinglotbackend.applicationlogic.implementations;

import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.ParkingCalculatorUtil;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.ParkingCalendarUtil;
import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.core.entities.ParkingRates;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.applicationlogic.ParkingAttendant;
import co.com.ceiba.parkinglotbackend.core.services.InvoiceService;
import co.com.ceiba.parkinglotbackend.core.services.ParkingRatesService;
import co.com.ceiba.parkinglotbackend.core.services.VehicleService;
import co.com.ceiba.parkinglotbackend.exceptions.*;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleDataException;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleDoesNotExistException;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.ParkingValidation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ParkingAttendantImplementation implements ParkingAttendant {

    private final ParkingCalendarUtil parkingCalendarUtil;
    private final ParkingCalculatorUtil parkingCalculatorUtil;

    // Services
    private final InvoiceService invoiceService;
    private final VehicleService vehicleService;
    private final ParkingRatesService parkingRatesService;

    // Validations
    private final List<ParkingValidation> parkingValidations;

    public ParkingAttendantImplementation(InvoiceService invoiceService,
                                          VehicleService vehicleService,
                                          ParkingRatesService parkingRatesService,
                                          List<ParkingValidation> parkingValidations,
                                          ParkingCalendarUtil parkingCalendarUtil,
                                          ParkingCalculatorUtil parkingCalculatorUtil) {
        this.invoiceService = invoiceService;
        this.vehicleService = vehicleService;
        this.parkingRatesService = parkingRatesService;
        this.parkingValidations = parkingValidations;
        this.parkingCalculatorUtil = parkingCalculatorUtil;
        this.parkingCalendarUtil = parkingCalendarUtil;
    }

    public Invoice vehicleCheckIn(Vehicle vehicleResponse) throws BaseException {
        Optional<Vehicle> vehicle;

        if (null == vehicleResponse || isValidCheckInResponse(vehicleResponse)) {
            throw new VehicleDataException();
        }

        vehicle = Optional.ofNullable(vehicleService.getNewVehicle(vehicleResponse.getLicensePlate(),
                vehicleResponse.getVehicleType().getName(), vehicleResponse.getCylinderCapacity().get()));

        validateVehicleEntry(vehicle);

        vehicle = vehicleService.add(vehicle);

        if (!vehicle.isPresent()) {
            throw new VehicleDoesNotExistException();
        }

        ParkingRates currentParkingRates = parkingRatesService.getCurrentParkingRatesFor(vehicle.get().getVehicleType(),
                vehicle.get().getCylinderCapacity());

        if (!Optional.ofNullable(currentParkingRates).isPresent()) {
            throw new VehicleDataException();
        }

        Invoice invoice = new Invoice(vehicle.get(), parkingCalendarUtil.getTodayDate(), currentParkingRates);
        invoice = invoiceService.save(invoice);

        return invoice;
    }

    private void validateVehicleEntry(Optional<Vehicle> vehicle) throws BaseException {
        for (ParkingValidation parkingValidation : parkingValidations) {
            parkingValidation.execute(vehicle);
        }
    }

    private Boolean isValidCheckInResponse(Vehicle vehicleResponse) {
        return null == vehicleResponse.getVehicleType()
                || null == vehicleResponse.getVehicleType().getName()
                || null == vehicleResponse.getLicensePlate()
                || !vehicleResponse.getCylinderCapacity().isPresent();
    }

    public Invoice vehicleCheckOut(String licensePlate) throws BaseException {

        if (null == licensePlate) {
            throw new VehicleDataException();
        }

        Optional<Invoice> invoice = invoiceService.getVehicleInParking(licensePlate);
        if (!invoice.isPresent()) {
            throw new VehicleDoesNotExistException();
        }

        Long price = parkingCalculatorUtil.calculatePrice(invoice.get().getParkingRates(), invoice.get().getEntryDate());
        invoice.get().setPrice(Optional.ofNullable(price));
        invoice.get().setDepartureDate(Optional.of(parkingCalendarUtil.getTodayDate()));
        invoiceService.save(invoice.get());

        return invoice.get();
    }
}
