package co.com.ceiba.parkinglotbackend.testdatabuilder;

import co.com.ceiba.parkinglotbackend.core.entities.ParkingRates;

public class ParkingRatesTestDataBuilder {

    private static final Long HOUR_PRICE = 500L;
    private static final Long DAY_PRICE = 4000L;
    private static final Long EXTRA_PRICE = 2000L;

    private Long hourPrice;
    private Long dayPrice;
    private Long extraPrice;

    public ParkingRatesTestDataBuilder() {
        this.hourPrice = HOUR_PRICE;
        this.dayPrice = DAY_PRICE;
        this.extraPrice = EXTRA_PRICE;
    }

    public ParkingRatesTestDataBuilder withHourPrice(Long hourPrice) {
        this.hourPrice = hourPrice;
        return this;
    }

    public ParkingRatesTestDataBuilder withDayPrice(Long dayPrice) {
        this.dayPrice = dayPrice;
        return this;
    }

    public ParkingRatesTestDataBuilder withExtraPrice(Long extraPrice) {
        this.extraPrice = extraPrice;
        return this;
    }

    public ParkingRates build() {
        return new ParkingRates(this.hourPrice, this.dayPrice, this.extraPrice);
    }
}
