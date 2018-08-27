package co.com.ceiba.parkinglotbackend.applicationlogic.implementations;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.core.entities.ParkingRates;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.applicationlogic.ParkingAttendant;
import co.com.ceiba.parkinglotbackend.core.services.InvoiceService;
import co.com.ceiba.parkinglotbackend.core.services.ParkingRatesService;
import co.com.ceiba.parkinglotbackend.core.services.VehicleService;
import co.com.ceiba.parkinglotbackend.core.services.VehicleTypeService;
import co.com.ceiba.parkinglotbackend.exceptions.*;
import co.com.ceiba.parkinglotbackend.utils.ParkingCalculatorUtil;
import co.com.ceiba.parkinglotbackend.utils.VehicleTypeEnum;
import co.com.ceiba.parkinglotbackend.validations.ParkingValidation;
import co.com.ceiba.parkinglotbackend.validations.ParkingValidationFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ParkingAttendantImplementation implements ParkingAttendant {

    private InvoiceService invoiceService;
    private VehicleService vehicleService;
    private ParkingRatesService parkingRatesService;

    public ParkingAttendantImplementation(InvoiceService invoiceService, VehicleService vehicleService,
                                          ParkingRatesService parkingRatesService, VehicleTypeService vehicleTypeService) {
        this.invoiceService = invoiceService;
        this.vehicleService = vehicleService;
        this.parkingRatesService = parkingRatesService;
    }

    public Invoice vehicleCheckIn(String licensePlate, VehicleTypeEnum vehicleTypeEnum, Integer cylinderCapacity) throws Exception {
        Optional<Vehicle> vehicle = vehicleService.get(Optional.ofNullable(licensePlate));

        if (!vehicle.isPresent()) {
            vehicle = Optional.ofNullable(vehicleService.getNewVehicle(licensePlate, vehicleTypeEnum, cylinderCapacity));
            vehicleService.add(vehicle);
        }

        validateVehicleEntry(vehicle);

        ParkingRates currentParkingRates = parkingRatesService.getCurrentParkingRatesFor(vehicle.get().getVehicleType());

        if (!Optional.ofNullable(currentParkingRates).isPresent()) {
            throw new VehicleDataException();
        }

        Invoice invoice = new Invoice(vehicle.get(), LocalDateTime.now(), currentParkingRates);
        invoiceService.save(invoice);

        return invoice;
    }

    private void validateVehicleEntry(Optional<Vehicle> vehicle) throws Exception {
        List<ParkingValidation> parkingValidations = new ArrayList<>();
        parkingValidations.add(ParkingValidationFactory.getInstance(ParkingValidationFactory.ParkingValidationType.VEHICLE_DATA_VALIDATION));
        parkingValidations.add(ParkingValidationFactory.getInstance(ParkingValidationFactory.ParkingValidationType.NO_SPACE_AVAILABLE));
        parkingValidations.add(ParkingValidationFactory.getInstance(ParkingValidationFactory.ParkingValidationType.INVALID_DAY_LICENSE_PLATE));

        for (ParkingValidation parkingValidation : parkingValidations) {
            parkingValidation.execute(vehicle);
        }
    }

    public Invoice vehicleCheckOut(String licensePlate, LocalDateTime departureDate) throws VehicleDoesNotExistException,
            InvalidDatesException {
        Optional<Invoice> invoice = invoiceService.getVehicleInParking(licensePlate);
        if (!invoice.isPresent()) {
            throw new VehicleDoesNotExistException();
        }

        try {
            Long price = ParkingCalculatorUtil.calculatePrice(invoice.get().getParkingRates(),
                    invoice.get().getEntryDate(), departureDate);
            invoice.get().setPrice(price);
            invoice.get().setDepartureDate(departureDate);
        } catch (InvalidDatesException e) {
            throw new InvalidDatesException();
        }

        return invoice.get();
    }

}
