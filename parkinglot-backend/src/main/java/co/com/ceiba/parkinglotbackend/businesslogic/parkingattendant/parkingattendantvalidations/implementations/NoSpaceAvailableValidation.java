package co.com.ceiba.parkinglotbackend.businesslogic.parkingattendant.parkingattendantvalidations.implementations;

import co.com.ceiba.parkinglotbackend.businesslogic.parkingattendant.parkingattendantvalidations.ParkingValidation;
import co.com.ceiba.parkinglotbackend.persistence.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.persistence.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.businesslogic.InvoiceService;
import co.com.ceiba.parkinglotbackend.businesslogic.VehicleTypeService;
import co.com.ceiba.parkinglotbackend.exceptions.BaseException;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.NoSpaceAvailableException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NoSpaceAvailableValidation implements ParkingValidation {

    private final InvoiceService invoiceService;
    private final VehicleTypeService vehicleTypeService;

    public NoSpaceAvailableValidation(InvoiceService invoiceService, VehicleTypeService vehicleTypeService) {
        this.invoiceService = invoiceService;
        this.vehicleTypeService = vehicleTypeService;
    }

    public void execute(Optional<Vehicle> vehicle) throws BaseException {
        if (vehicle.isPresent()) {
            Long spacesInUse = invoiceService.getParkingSpacesCountInUseForVehicleType(
                    vehicle.get().getVehicleType().getName());
            Optional<VehicleType> vehicleType = vehicleTypeService.getCurrentVehicleType(
                    vehicle.get().getVehicleType().getName());

            if (!vehicleType.isPresent()
                    || vehicleType.get().getTotalPlaces() <= spacesInUse) {
                throw new NoSpaceAvailableException();
            }
        }
    }
}
