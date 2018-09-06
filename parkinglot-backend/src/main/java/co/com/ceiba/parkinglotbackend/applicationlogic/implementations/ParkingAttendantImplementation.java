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

    private final ThreadLocal<ParkingCalendarUtil> parkingCalendarUtil = new ThreadLocal<>();
    private final ThreadLocal<ParkingCalculatorUtil> parkingCalculatorUtil = new ThreadLocal<>();

    // Services
    private final ThreadLocal<InvoiceService> invoiceService = new ThreadLocal<>();
    private final ThreadLocal<VehicleService> vehicleService = new ThreadLocal<>();
    private final ThreadLocal<ParkingRatesService> parkingRatesService = new ThreadLocal<>();

    // Validations
    private final ThreadLocal<List<ParkingValidation>> parkingValidations = new ThreadLocal<>();

    public ParkingAttendantImplementation(InvoiceService invoiceService,
                                          VehicleService vehicleService,
                                          ParkingRatesService parkingRatesService,
                                          List<ParkingValidation> parkingValidations,
                                          ParkingCalendarUtil parkingCalendarUtil,
                                          ParkingCalculatorUtil parkingCalculatorUtil) {
        this.invoiceService.set(invoiceService);
        this.vehicleService.set(vehicleService);
        this.parkingRatesService.set(parkingRatesService);
        this.parkingValidations.set(parkingValidations);
        this.parkingCalculatorUtil.set(parkingCalculatorUtil);
        this.parkingCalendarUtil.set(parkingCalendarUtil);
    }

    public Invoice vehicleCheckIn(Vehicle vehicleResponse) throws BaseException {
        Optional<Vehicle> vehicle;

        if (null == vehicleResponse || isValidCheckInResponse(vehicleResponse)) {
            throw new VehicleDataException();
        }

        vehicle = Optional.ofNullable(vehicleService.get().getNewVehicle(vehicleResponse.getLicensePlate(),
                vehicleResponse.getVehicleType().getName(), vehicleResponse.getCylinderCapacity().get()));

        validateVehicleEntry(vehicle);

        vehicle = vehicleService.get().add(vehicle);

        if (!vehicle.isPresent()) {
            throw new VehicleDoesNotExistException();
        }

        ParkingRates currentParkingRates = parkingRatesService.get().getCurrentParkingRatesFor(vehicle.get().getVehicleType(),
                vehicle.get().getCylinderCapacity());

        if (!Optional.ofNullable(currentParkingRates).isPresent()) {
            throw new VehicleDataException();
        }

        Invoice invoice = new Invoice(vehicle.get(), parkingCalendarUtil.get().getTodayDate(), currentParkingRates);
        invoice = invoiceService.get().save(invoice);

        return invoice;
    }

    private void validateVehicleEntry(Optional<Vehicle> vehicle) throws BaseException {
        for (ParkingValidation parkingValidation : parkingValidations.get()) {
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

        Optional<Invoice> invoice = invoiceService.get().getVehicleInParking(licensePlate);
        if (!invoice.isPresent()) {
            throw new VehicleDoesNotExistException();
        }

        Long price = parkingCalculatorUtil.get().calculatePrice(invoice.get().getParkingRates(), invoice.get().getEntryDate());
        invoice.get().setPrice(Optional.ofNullable(price));
        invoice.get().setDepartureDate(Optional.of(parkingCalendarUtil.get().getTodayDate()));
        invoiceService.get().save(invoice.get());

        return invoice.get();
    }
}
