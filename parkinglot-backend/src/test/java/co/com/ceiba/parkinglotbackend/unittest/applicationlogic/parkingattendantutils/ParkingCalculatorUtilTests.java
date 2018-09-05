package co.com.ceiba.parkinglotbackend.unittest.applicationlogic.parkingattendantutils;

import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.ParkingCalendarUtil;
import co.com.ceiba.parkinglotbackend.core.entities.ParkingRates;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvalidDatesException;
import co.com.ceiba.parkinglotbackend.testdatabuilder.ParkingCalendarTestDataBuilder;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.ParkingRatesTestDataBuilder;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.ParkingCalculatorUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
/*
@RunWith(SpringJUnit4ClassRunner.class)
public class ParkingCalculatorUtilTests {

    private ParkingCalculatorUtil sut;

    @Mock
    private ParkingCalendarUtil mockParkingCalendarUtil;

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
        sut = new ParkingCalculatorUtil(mockParkingCalendarUtil);
    }

    @After
    public void tearDown() {
        sut = null;
        parkingRates = null;
        parkingRatesTestDataBuilder = null;
        sunday = null;
        monday = null;
        parkingCalendarTestDataBuilder = null;
    }

    @Test
    public void calculateCorrectPrice() throws InvalidDatesException {
        Long price = sut.calculatePrice(parkingRates, sunday, monday);
        assertEquals("Incorrect total price", parkingRates.getDayPrice() + parkingRates.getExtraPrice(),
                price.longValue());

        price = sut.calculatePrice(parkingRates, sunday, monday.plusMinutes(1));

        assertEquals("Incorrect total price", parkingRates.getDayPrice() +
                        parkingRates.getHourPrice() + parkingRates.getExtraPrice(), price.longValue());

        price = sut.calculatePrice(parkingRates, sunday, monday.plusMinutes(59));
        assertEquals("Incorrect total price", parkingRates.getDayPrice() +
                parkingRates.getHourPrice() + parkingRates.getExtraPrice(), price.longValue());
    }

    @Test(expected = InvalidDatesException.class)
    public void calculatePriceWithBadDates() throws InvalidDatesException {
        sut.calculatePrice(parkingRates, monday, sunday);
    }

    @Test
    public void calculatePriceWithoutExtraPrice() throws InvalidDatesException {
        parkingRates = parkingRatesTestDataBuilder.withExtraPrice(0L).build();
        Long price = sut.calculatePrice(parkingRates, sunday, monday);
        assertEquals("Incorrect total price", parkingRates.getDayPrice().longValue(), price.longValue());
    }

    @Test
    public void calculateFreePrice() throws InvalidDatesException {
        Long price = sut.calculatePrice(parkingRates, sunday, sunday);
        assertEquals("Total price must be zero", 0, price.longValue());

        price = sut.calculatePrice(parkingRates, sunday, sunday.plusMinutes(5));
        assertEquals("Total price must be zero", 0, price.longValue());
    }
}*/
