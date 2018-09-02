package co.com.ceiba.parkinglotbackend.testdatabuilder.dtos;

import co.com.ceiba.parkinglotbackend.dtos.InvoiceDTO;

import java.time.LocalDateTime;
import java.util.Optional;

public class InvoiceDTOTestDataBuilder {

    private static final String VEHICLE_LICENSE_PLATE = "ssh42a";
    private static final LocalDateTime ENTRY_DATE = LocalDateTime.now().minusDays(1);
    private static final Optional<LocalDateTime> DEPARTURE_DATE = Optional.of(LocalDateTime.now());
    private static final Optional<Long> PRICE = Optional.of(8000L);
    private static final String VEHICLE_TYPE_NAME = "CAR";

    private String vehicleLicensePlate;
    private LocalDateTime entryDate;
    private Optional<LocalDateTime> departureDate;
    private Optional<Long> price;
    private String vehicleTypeName;

    public InvoiceDTOTestDataBuilder() {
        this.vehicleLicensePlate = VEHICLE_LICENSE_PLATE;
        this.entryDate = ENTRY_DATE;
        this.departureDate = DEPARTURE_DATE;
        this.price = PRICE;
        this.vehicleTypeName = VEHICLE_TYPE_NAME;
    }

    public InvoiceDTOTestDataBuilder withVehicleLicensePlate(String vehicleLicensePlate) {
        this.vehicleLicensePlate = vehicleLicensePlate;
        return this;
    }

    public InvoiceDTOTestDataBuilder withEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public InvoiceDTOTestDataBuilder withDepartureDate(Optional<LocalDateTime> departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public InvoiceDTOTestDataBuilder withPrice(Optional<Long> price) {
        this.price = price;
        return this;
    }

    public InvoiceDTOTestDataBuilder withVehicleTypeName(String vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
        return this;
    }

    public InvoiceDTO build() {
        return new InvoiceDTO(this.vehicleLicensePlate, this.entryDate, this.departureDate, this.price, this.vehicleTypeName);
    }
}
