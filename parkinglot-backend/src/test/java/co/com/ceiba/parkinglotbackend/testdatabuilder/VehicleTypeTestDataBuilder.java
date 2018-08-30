package co.com.ceiba.parkinglotbackend.testdatabuilder;

import co.com.ceiba.parkinglotbackend.core.entities.VehicleType;

public class VehicleTypeTestDataBuilder {

    private static final String NAME = "MOTORCYCLE";
    private static final Integer TOTAL_PLACES = 10;

    private String name;
    private Integer totalPlaces;

    public VehicleTypeTestDataBuilder() {
        this.name = NAME;
        this.totalPlaces = TOTAL_PLACES;
    }

    public VehicleTypeTestDataBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public VehicleTypeTestDataBuilder withTotalPlaces(Integer totalPlaces) {
        this.totalPlaces = totalPlaces;
        return this;
    }

    public VehicleType build() {
        return new VehicleType(this.name, this.totalPlaces);
    }
}
