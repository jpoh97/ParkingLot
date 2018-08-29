package co.com.ceiba.parkinglotbackend.validations.implementations;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.core.services.InvoiceService;
import co.com.ceiba.parkinglotbackend.exceptions.BaseException;
import co.com.ceiba.parkinglotbackend.exceptions.Implementations.VehicleAlreadyExistsInParkingLotException;
import co.com.ceiba.parkinglotbackend.validations.ParkingValidation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VehicleAlreadyExistsInParkingLotValidation implements ParkingValidation {

    private InvoiceService invoiceService;

    public VehicleAlreadyExistsInParkingLotValidation(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public void execute(Optional<Vehicle> vehicle) throws BaseException {
        Optional<Invoice> invoice = invoiceService.getVehicleInParking(vehicle.get().getLicensePlate());
        if (invoice.isPresent()) {
            throw new VehicleAlreadyExistsInParkingLotException();
        }
    }
}
