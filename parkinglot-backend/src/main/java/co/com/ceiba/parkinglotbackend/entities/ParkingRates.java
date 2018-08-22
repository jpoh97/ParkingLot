package co.com.ceiba.parkinglotbackend.entities;

public enum ParkingRates {
    CAR_HOUR_PRICE(1000),
    CAR_DAY_PRICE(8000),
    MOTORCYCLE_HOUR_PRICE(500),
    MOTORCYCLE_DAY_PRICE(4000);

    private final int id;
    ParkingRates(int id) { this.id = id; }
    public int getValue() { return id; }
}
