package co.com.ceiba.parkinglotbackend.testdatabuilder.entities;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.core.entities.ParkingRates;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;

import java.time.LocalDateTime;

public class InvoiceTestDataBuilder {

    private static final Vehicle VEHICLE = new VehicleTestDataBuilder().build();
    private static final LocalDateTime ENTRY_DATE = LocalDateTime.now();
    private static final ParkingRates PARKING_RATES = new ParkingRatesTestDataBuilder().build();

    private Vehicle vehicle;
    private LocalDateTime entryDate;
    private ParkingRates parkingRates;

    public InvoiceTestDataBuilder() {
        this.vehicle = VEHICLE;
        this.entryDate = ENTRY_DATE;
        this.parkingRates = PARKING_RATES;
    }

    public InvoiceTestDataBuilder withVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public InvoiceTestDataBuilder withEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public InvoiceTestDataBuilder withParkingRates(ParkingRates parkingRates) {
        this.parkingRates = parkingRates;
        return this;
    }

    public Invoice build() {
        return new Invoice(this.vehicle, this.entryDate, this.parkingRates);
    }
}
