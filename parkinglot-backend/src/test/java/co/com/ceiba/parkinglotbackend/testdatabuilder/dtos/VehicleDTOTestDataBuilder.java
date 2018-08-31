package co.com.ceiba.parkinglotbackend.testdatabuilder.dtos;

import co.com.ceiba.parkinglotbackend.dtos.VehicleDTO;

import java.util.Optional;

public class VehicleDTOTestDataBuilder {

    private static final String LICENSE_PLATE = "ssh42a";
    private static final Optional<Integer> CYLINDER_CAPACITY = Optional.ofNullable(42);
    private static final String VEHICLE_TYPE_NAME = "CAR";

    private String licensePlate;
    private Optional<Integer> cylinderCapacity;
    private String vehicleTypeName;

    public VehicleDTOTestDataBuilder() {
        this.licensePlate = LICENSE_PLATE;
        this.cylinderCapacity = CYLINDER_CAPACITY;
        this.vehicleTypeName = VEHICLE_TYPE_NAME;
    }

    public VehicleDTOTestDataBuilder withLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
        return this;
    }

    public VehicleDTOTestDataBuilder withCylinderCapacity(Optional<Integer> cylinderCapacity) {
        this.cylinderCapacity = cylinderCapacity;
        return this;
    }

    public VehicleDTOTestDataBuilder withVehicleTypeName(String vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
        return this;
    }

    public VehicleDTO build() {
        return new VehicleDTO(this.licensePlate, this.cylinderCapacity, this.vehicleTypeName);
    }
}
