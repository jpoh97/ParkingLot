package co.com.ceiba.parkinglotbackend.dtos;

import java.time.LocalDateTime;
import java.util.Optional;

public class InvoiceDTO {
    private String vehicleLicensePlate;
    private LocalDateTime entryDate;
    private Optional<LocalDateTime> departureDate;
    private Optional<Long> price;
    private String vehicleTypeName;

    public InvoiceDTO() {}

    public InvoiceDTO(String vehicleLicensePlate, LocalDateTime entryDate, Optional<LocalDateTime> departureDate,
                      Optional<Long> price, String vehicleTypeName) {
        this.vehicleLicensePlate = vehicleLicensePlate;
        this.entryDate = entryDate;
        this.departureDate = departureDate;
        this.price = price;
        this.vehicleTypeName = vehicleTypeName;
    }

    public String getVehicleLicensePlate() {
        return vehicleLicensePlate;
    }

    public void setVehicleLicensePlate(String vehicleLicensePlate) {
        this.vehicleLicensePlate = vehicleLicensePlate;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public Optional<LocalDateTime> getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Optional<LocalDateTime> departureDate) {
        this.departureDate = departureDate;
    }

    public Optional<Long> getPrice() {
        return price;
    }

    public void setPrice(Optional<Long> price) {
        this.price = price;
    }

    public String getVehicleTypeName() {
        return vehicleTypeName;
    }

    public void setVehicleTypeName(String vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
    }

}
