package co.com.ceiba.parkinglotbackend.validations.implementations;

import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.core.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.core.services.InvoiceService;
import co.com.ceiba.parkinglotbackend.core.services.VehicleTypeService;
import co.com.ceiba.parkinglotbackend.exceptions.Implementations.NoSpaceAvailableException;
import co.com.ceiba.parkinglotbackend.validations.ParkingValidation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NoSpaceAvailableValidation implements ParkingValidation {

    private InvoiceService invoiceService;
    private VehicleTypeService vehicleTypeService;

    public NoSpaceAvailableValidation(InvoiceService invoiceService, VehicleTypeService vehicleTypeService) {
        this.invoiceService = invoiceService;
        this.vehicleTypeService = vehicleTypeService;
    }

    public void execute(Optional<Vehicle> vehicle) throws NoSpaceAvailableException {
        Long spacesInUse = invoiceService.getParkingSpacesCountInUseForVehicleType(
                vehicle.get().getVehicleType().getName());
        Optional<VehicleType> vehicleType = vehicleTypeService.getCurrentVehicleType(
                vehicle.get().getVehicleType().getName());

        if(!vehicleType.isPresent()
                || vehicleType.get().getTotalPlaces().intValue() <= spacesInUse.longValue()) {
            throw new NoSpaceAvailableException();
        }
    }
}
