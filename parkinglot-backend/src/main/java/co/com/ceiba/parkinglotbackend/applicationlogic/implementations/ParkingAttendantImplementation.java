package co.com.ceiba.parkinglotbackend.applicationlogic.implementations;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.core.entities.ParkingRates;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.applicationlogic.ParkingAttendant;
import co.com.ceiba.parkinglotbackend.core.services.InvoiceService;
import co.com.ceiba.parkinglotbackend.core.services.ParkingRatesService;
import co.com.ceiba.parkinglotbackend.core.services.VehicleService;
import co.com.ceiba.parkinglotbackend.exceptions.*;
import co.com.ceiba.parkinglotbackend.exceptions.Implementations.InvalidDatesException;
import co.com.ceiba.parkinglotbackend.exceptions.Implementations.VehicleDataException;
import co.com.ceiba.parkinglotbackend.exceptions.Implementations.VehicleDoesNotExistException;
import co.com.ceiba.parkinglotbackend.utils.ParkingCalculatorUtil;
import co.com.ceiba.parkinglotbackend.validations.ParkingValidation;
import co.com.ceiba.parkinglotbackend.validations.InvalidDayLicensePlateValidation;
import co.com.ceiba.parkinglotbackend.validations.implementations.NoSpaceAvailableValidation;
import co.com.ceiba.parkinglotbackend.validations.implementations.VehicleAlreadyExistsInParkingLotValidation;
import co.com.ceiba.parkinglotbackend.validations.implementations.VehicleDataValidation;
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
    private InvalidDayLicensePlateValidation invalidDayLicensePlateValidation;
    private NoSpaceAvailableValidation noSpaceAvailableValidation;
    private VehicleDataValidation vehicleDataValidation;
    private VehicleAlreadyExistsInParkingLotValidation vehicleAlreadyExistsInParkingLotValidation;

    public ParkingAttendantImplementation(InvoiceService invoiceService,
                                          VehicleService vehicleService,
                                          ParkingRatesService parkingRatesService,
                                          InvalidDayLicensePlateValidation invalidDayLicensePlateValidation,
                                          NoSpaceAvailableValidation noSpaceAvailableValidation,
                                          VehicleDataValidation vehicleDataValidation,
                                          VehicleAlreadyExistsInParkingLotValidation vehicleAlreadyExistsInParkingLotValidation) {
        this.invoiceService = invoiceService;
        this.vehicleService = vehicleService;
        this.parkingRatesService = parkingRatesService;
        this.invalidDayLicensePlateValidation = invalidDayLicensePlateValidation;
        this.noSpaceAvailableValidation = noSpaceAvailableValidation;
        this.vehicleDataValidation = vehicleDataValidation;
        this.vehicleAlreadyExistsInParkingLotValidation = vehicleAlreadyExistsInParkingLotValidation;
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
        parkingValidations.add(noSpaceAvailableValidation);
        parkingValidations.add(vehicleDataValidation);
        parkingValidations.add(vehicleAlreadyExistsInParkingLotValidation);

        invalidDayLicensePlateValidation.execute(vehicle, entryDate);

        for (ParkingValidation parkingValidation : parkingValidations) {
            parkingValidation.execute(vehicle);
        }
    }

    public Invoice vehicleCheckOut(String licensePlate, LocalDateTime departureDate) throws VehicleDoesNotExistException,
            InvalidDatesException, VehicleDataException {

        if (null == licensePlate
                || null == departureDate) {
            throw new VehicleDataException();
        }

        Optional<Invoice> invoice = invoiceService.getVehicleInParking(licensePlate);
        if (!invoice.isPresent()) {
            throw new VehicleDoesNotExistException();
        }

        try {
            Long price = ParkingCalculatorUtil.calculatePrice(invoice.get().getParkingRates(),
                    invoice.get().getEntryDate(), departureDate);
            invoice.get().setPrice(Optional.ofNullable(price));
            invoice.get().setDepartureDate(Optional.ofNullable(departureDate));
            invoiceService.save(invoice.get());
        } catch (InvalidDatesException e) {
            throw new InvalidDatesException();
        }

        return invoice.get();
    }
}
