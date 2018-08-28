package co.com.ceiba.parkinglotbackend.core.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    @NotNull
    private Vehicle vehicle;
    @NotNull
    private LocalDateTime entryDate;
    private LocalDateTime departureDate;
    private Long price;
    @ManyToOne(fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    @NotNull
    private ParkingRates parkingRates;

    public Invoice(Vehicle vehicle, LocalDateTime entryDate, ParkingRates parkingRates) {
        this.vehicle = vehicle;
        this.entryDate = entryDate;
        this.parkingRates = parkingRates;
    }

    public Invoice() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public Optional<LocalDateTime> getDepartureDate() {
        return Optional.ofNullable(departureDate);
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public Optional<Long> getPrice() {
        return Optional.ofNullable(price);
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public ParkingRates getParkingRates() {
        return parkingRates;
    }

    public void setParkingRates(ParkingRates parkingRates) {
        this.parkingRates = parkingRates;
    }
}
