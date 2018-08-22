package co.com.ceiba.parkinglotbackend.services.implementations;

import co.com.ceiba.parkinglotbackend.entities.Invoice;
import co.com.ceiba.parkinglotbackend.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.exceptions.VehicleDataErrorException;
import co.com.ceiba.parkinglotbackend.services.interfaces.InvoiceService;

import java.util.Optional;

public class InvoiceServiceImplementation implements InvoiceService {

    private Invoice invoice;

    public Invoice create(Optional<Vehicle> vehicle) throws VehicleDataErrorException {
        if(!vehicle.isPresent() || vehicle.get().getLicensePlate() == null) { throw new VehicleDataErrorException(); }
        // validar capacidad

        // validar dia que sea habil
        
        return invoice;
    }

    public Invoice generate() {

        return invoice;
    }
}
