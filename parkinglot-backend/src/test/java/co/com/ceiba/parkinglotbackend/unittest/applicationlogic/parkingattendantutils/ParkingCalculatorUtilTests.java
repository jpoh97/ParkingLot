package co.com.ceiba.parkinglotbackend.unittest.applicationlogic.parkingattendantutils;

import co.com.ceiba.parkinglotbackend.core.entities.ParkingRates;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvalidDatesException;
import co.com.ceiba.parkinglotbackend.testdatabuilder.ParkingCalendarTestDataBuilder;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.ParkingRatesTestDataBuilder;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.ParkingCalculatorUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ParkingCalculatorUtilTests {

    private ParkingRates parkingRates;
    private ParkingRatesTestDataBuilder parkingRatesTestDataBuilder;
    private ParkingCalendarTestDataBuilder parkingCalendarTestDataBuilder;
    private LocalDateTime sunday;
    private LocalDateTime monday;

    @Before
    public void setUp() {
        parkingRatesTestDataBuilder = new ParkingRatesTestDataBuilder();
        parkingCalendarTestDataBuilder = new ParkingCalendarTestDataBuilder();
        parkingRates = parkingRatesTestDataBuilder.build();
        sunday = parkingCalendarTestDataBuilder.build();
        monday = parkingCalendarTestDataBuilder.build().plusDays(1);
    }

    @After
    public void tearDown() {
        parkingRates = null;
        parkingRatesTestDataBuilder = null;
        sunday = null;
        monday = null;
        parkingCalendarTestDataBuilder = null;
    }

    @Test
    public void calculateCorrectPrice() throws InvalidDatesException {
        Long price = ParkingCalculatorUtil.calculatePrice(parkingRates, sunday, monday);
        assertEquals("Incorrect total price", parkingRates.getDayPrice() + parkingRates.getExtraPrice(),
                price.longValue());

        price = ParkingCalculatorUtil.calculatePrice(parkingRates, sunday, monday.plusMinutes(1));
        assertEquals("Incorrect total price", parkingRates.getDayPrice() +
                        parkingRates.getHourPrice() + parkingRates.getExtraPrice(), price.longValue());

        price = ParkingCalculatorUtil.calculatePrice(parkingRates, sunday, monday.plusMinutes(59));
        assertEquals("Incorrect total price", parkingRates.getDayPrice() +
                parkingRates.getHourPrice() + parkingRates.getExtraPrice(), price.longValue());
    }

    @Test(expected = InvalidDatesException.class)
    public void calculatePriceWithBadDates() throws InvalidDatesException {
        ParkingCalculatorUtil.calculatePrice(parkingRates, monday, sunday);
    }

    @Test
    public void calculatePriceWithoutExtraPrice() throws InvalidDatesException {
        parkingRates = parkingRatesTestDataBuilder.withExtraPrice(0L).build();
        Long price = ParkingCalculatorUtil.calculatePrice(parkingRates, sunday, monday);
        assertEquals("Incorrect total price", parkingRates.getDayPrice().longValue(), price.longValue());
    }

    @Test
    public void calculateFreePrice() throws InvalidDatesException {
        Long price = ParkingCalculatorUtil.calculatePrice(parkingRates, sunday, sunday);
        assertEquals("Total price must be zero", 0, price.longValue());

        price = ParkingCalculatorUtil.calculatePrice(parkingRates, sunday, sunday.plusMinutes(5));
        assertEquals("Total price must be zero", 0, price.longValue());
    }
}
