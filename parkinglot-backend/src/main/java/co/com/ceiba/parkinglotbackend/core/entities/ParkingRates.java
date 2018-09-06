package co.com.ceiba.parkinglotbackend.core.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class ParkingRates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private Long hourPrice;
    @NotNull
    private Long dayPrice;
    private Long extraPrice;
    @NotNull
    private LocalDate creationDate;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @NotNull
    private VehicleType vehicleType;
    @NotNull
    private Boolean active;

    public ParkingRates() {
    }

    /**
     * Constructor for unit test
     */
    public ParkingRates(@NotNull Long hourPrice, @NotNull Long dayPrice, Long extraPrice) {
        this.hourPrice = hourPrice;
        this.dayPrice = dayPrice;
        this.extraPrice = extraPrice;
        this.creationDate = LocalDate.now();
        this.active = true;
        this.vehicleType = new VehicleType();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getHourPrice() {
        return hourPrice;
    }

    public void setHourPrice(Long hourPrice) {
        this.hourPrice = hourPrice;
    }

    public Long getDayPrice() {
        return dayPrice;
    }

    public void setDayPrice(Long dayPrice) {
        this.dayPrice = dayPrice;
    }

    public Long getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(Long extraPrice) {
        this.extraPrice = extraPrice;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
