package co.com.ceiba.parkinglotbackend.core.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    @NotNull
    private String licensePlate;
    private Integer cylinderCapacity;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @NotNull
    private VehicleType vehicleType;
    @NotNull
    private LocalDateTime creationDate;

    public Vehicle(@NotNull String licensePlate, Integer cylinderCapacity, @NotNull VehicleType vehicleType, @NotNull LocalDateTime creationDate) {
        this.licensePlate = licensePlate;
        this.cylinderCapacity = cylinderCapacity;
        this.vehicleType = vehicleType;
        this.creationDate = creationDate;
    }

    public Vehicle() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Optional<Integer> getCylinderCapacity() {
        return Optional.ofNullable(cylinderCapacity);
    }

    public void setCylinderCapacity(Optional<Integer> cylinderCapacity) {
        if (cylinderCapacity.isPresent()) {
            this.cylinderCapacity = cylinderCapacity.get();
        }
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
