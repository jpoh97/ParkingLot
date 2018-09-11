package co.com.ceiba.parkinglotbackend.testdatabuilder.entities;

import co.com.ceiba.parkinglotbackend.persistence.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.persistence.entities.VehicleType;

import java.time.LocalDateTime;

public class VehicleTestDataBuilder {

    private static final String LICENSE_PLATE = "ssh42a";
    private static final Integer CC = 42;
    private static final VehicleType VEHICLE_TYPE = new VehicleTypeTestDataBuilder().build();
    private static final LocalDateTime CREATION_DATE = LocalDateTime.now();

    private String licensePlate;
    private Integer cylinderCapacity;
    private VehicleType vehicleType;
    private LocalDateTime creationDate;

    public VehicleTestDataBuilder() {
        this.licensePlate = LICENSE_PLATE;
        this.cylinderCapacity = CC;
        this.vehicleType = VEHICLE_TYPE;
        this.creationDate = CREATION_DATE;
    }

    public VehicleTestDataBuilder withLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
        return this;
    }

    public VehicleTestDataBuilder withCylinderCapacity(Integer cylinderCapacity) {
        this.cylinderCapacity = cylinderCapacity;
        return this;
    }

    public VehicleTestDataBuilder withVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public VehicleTestDataBuilder withCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public Vehicle build() {
        return new Vehicle(this.licensePlate, this.cylinderCapacity, this.vehicleType, this.creationDate);
    }
}
