package co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.implementations;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.core.services.InvoiceService;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleAlreadyExistsInParkingLotException;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.ParkingValidation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VehicleAlreadyExistsInParkingLotValidation implements ParkingValidation {

    private InvoiceService invoiceService;

    public VehicleAlreadyExistsInParkingLotValidation(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public void execute(Optional<Vehicle> vehicle) throws VehicleAlreadyExistsInParkingLotException {
        Optional<Invoice> invoice = invoiceService.getVehicleInParking(vehicle.get().getLicensePlate());
        if (invoice.isPresent()) {
            throw new VehicleAlreadyExistsInParkingLotException();
        }
    }
}
