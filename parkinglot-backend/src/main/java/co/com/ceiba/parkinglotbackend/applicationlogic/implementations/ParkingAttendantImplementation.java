package co.com.ceiba.parkinglotbackend.applicationlogic.implementations;

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
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.ParkingCalculatorUtil;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.InvalidDayLicensePlateValidation;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.ParkingValidation;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.ParkingValidationFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ParkingAttendantImplementation implements ParkingAttendant {

    // Services
    private InvoiceService invoiceService;
    private VehicleService vehicleService;
    private ParkingRatesService parkingRatesService;

    // Validations
    private ParkingValidationFactory parkingValidationFactory;
    private InvalidDayLicensePlateValidation invalidDayLicensePlateValidation;

    public ParkingAttendantImplementation(InvoiceService invoiceService,
                                          VehicleService vehicleService,
                                          ParkingRatesService parkingRatesService,
                                          ParkingValidationFactory parkingValidationFactory,
                                          InvalidDayLicensePlateValidation invalidDayLicensePlateValidation) {
        this.invoiceService = invoiceService;
        this.vehicleService = vehicleService;
        this.parkingRatesService = parkingRatesService;
        this.parkingValidationFactory = parkingValidationFactory;
        this.invalidDayLicensePlateValidation = invalidDayLicensePlateValidation;
    }

    public Invoice vehicleCheckIn(Vehicle vehicleResponse, LocalDateTime entryDate) throws BaseException {
        Optional<Vehicle> vehicle;

        if (null == vehicleResponse.getVehicleType()
                || null == vehicleResponse.getVehicleType().getName()
                || null == vehicleResponse.getLicensePlate()
                || !vehicleResponse.getCylinderCapacity().isPresent()) {
            throw new VehicleDataException();
        }

        vehicle = Optional.ofNullable(vehicleService.getNewVehicle(vehicleResponse.getLicensePlate(),
                vehicleResponse.getVehicleType().getName(), vehicleResponse.getCylinderCapacity().get()));

        validateVehicleEntry(vehicle, entryDate);

        vehicle = vehicleService.add(vehicle);

        if (!vehicle.isPresent()) {
            throw new VehicleDoesNotExistException();
        }

        ParkingRates currentParkingRates = parkingRatesService.getCurrentParkingRatesFor(vehicle.get().getVehicleType(),
                vehicle.get().getCylinderCapacity());

        if (!Optional.ofNullable(currentParkingRates).isPresent()) {
            throw new VehicleDataException();
        }

        Invoice invoice = new Invoice(vehicle.get(), entryDate, currentParkingRates);
        invoiceService.save(invoice);

        return invoice;
    }

    private void validateVehicleEntry(Optional<Vehicle> vehicle, LocalDateTime entryDate) throws BaseException {
        List<ParkingValidation> parkingValidations = new ArrayList<>();
        parkingValidations.add(parkingValidationFactory.getParkingValidation(ParkingValidationFactory.ParkingValidationType.NO_SPACE_AVAILABLE));
        parkingValidations.add(parkingValidationFactory.getParkingValidation(ParkingValidationFactory.ParkingValidationType.VEHICLE_DATA));
        parkingValidations.add(parkingValidationFactory.getParkingValidation(ParkingValidationFactory.ParkingValidationType.VEHICLE_ALREADY_EXISTS_IN_PARKING_LOT));

        invalidDayLicensePlateValidation.execute(vehicle.get().getLicensePlate(), entryDate);

        for (ParkingValidation parkingValidation : parkingValidations) {
            parkingValidation.execute(vehicle);
        }
    }

    public Invoice vehicleCheckOut(String licensePlate, LocalDateTime departureDate) throws BaseException {

        if (null == licensePlate
                || null == departureDate) {
            throw new VehicleDataException();
        }

        Optional<Invoice> invoice = invoiceService.getVehicleInParking(licensePlate);
        if (!invoice.isPresent()) {
            throw new VehicleDoesNotExistException();
        }

        Long price = ParkingCalculatorUtil.calculatePrice(invoice.get().getParkingRates(), invoice.get().getEntryDate(), departureDate);
        invoice.get().setPrice(Optional.ofNullable(price));
        invoice.get().setDepartureDate(Optional.ofNullable(departureDate));
        invoiceService.save(invoice.get());

        return invoice.get();
    }
}
