package co.com.ceiba.parkinglotbackend.dtos;

import java.util.Optional;

public class VehicleDTO {
    private String licensePlate;
    private Optional<Integer> cylinderCapacity;
    private String vehicleTypeName;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Optional<Integer> getCylinderCapacity() {
        return cylinderCapacity;
    }

    public void setCylinderCapacity(Optional<Integer> cylinderCapacity) {
        this.cylinderCapacity = cylinderCapacity;
    }

    public String getVehicleTypeName() {
        return vehicleTypeName;
    }

    public void setVehicleTypeName(String vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
    }
}
