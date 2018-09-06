package co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.implementations;

import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.core.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.core.services.InvoiceService;
import co.com.ceiba.parkinglotbackend.core.services.VehicleTypeService;
import co.com.ceiba.parkinglotbackend.exceptions.BaseException;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.NoSpaceAvailableException;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantvalidations.ParkingValidation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NoSpaceAvailableValidation implements ParkingValidation {

    private final ThreadLocal<InvoiceService> invoiceService = new ThreadLocal<>();
    private final ThreadLocal<VehicleTypeService> vehicleTypeService = new ThreadLocal<>();

    public NoSpaceAvailableValidation(InvoiceService invoiceService, VehicleTypeService vehicleTypeService) {
        this.invoiceService.set(invoiceService);
        this.vehicleTypeService.set(vehicleTypeService);
    }

    public void execute(Optional<Vehicle> vehicle) throws BaseException {
        if (vehicle.isPresent()) {
            Long spacesInUse = invoiceService.get().getParkingSpacesCountInUseForVehicleType(
                    vehicle.get().getVehicleType().getName());
            Optional<VehicleType> vehicleType = vehicleTypeService.get().getCurrentVehicleType(
                    vehicle.get().getVehicleType().getName());

            if (!vehicleType.isPresent()
                    || vehicleType.get().getTotalPlaces() <= spacesInUse) {
                throw new NoSpaceAvailableException();
            }
        }
    }
}
