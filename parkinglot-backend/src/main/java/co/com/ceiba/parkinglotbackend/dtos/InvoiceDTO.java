package co.com.ceiba.parkinglotbackend.dtos;

import java.time.LocalDateTime;
import java.util.Optional;

public class InvoiceDTO {
    private String vehicleLicensePlate;
    private LocalDateTime entryDate;
    private Optional<LocalDateTime> departureDate;
    private Optional<Long> price;

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
}
