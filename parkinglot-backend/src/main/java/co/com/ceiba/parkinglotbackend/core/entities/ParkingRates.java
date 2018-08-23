package co.com.ceiba.parkinglotbackend.core.entities;

public enum ParkingRates {
    CAR_HOUR_PRICE(1000),
    CAR_DAY_PRICE(8000),
    MOTORCYCLE_HOUR_PRICE(500),
    MOTORCYCLE_DAY_PRICE(4000);

    private final Integer id;
    ParkingRates(Integer id) { this.id = id; }
    public int getValue() { return id; }
}
