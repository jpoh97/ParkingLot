package co.com.ceiba.parkinglotbackend.utils;

public enum VehicleTypeEnum {
    CAR("CAR"),
    MOTORCYCLE("MOTORCYCLE");

    private final String name;
    VehicleTypeEnum(String name) { this.name = name; }
    public String getValue() { return name; }
}
