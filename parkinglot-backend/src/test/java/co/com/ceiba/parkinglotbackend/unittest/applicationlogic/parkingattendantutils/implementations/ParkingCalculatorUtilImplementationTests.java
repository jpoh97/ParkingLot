package co.com.ceiba.parkinglotbackend.unittest.applicationlogic.parkingattendantutils.implementations;

import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.ParkingCalculatorUtil;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.implementations.ParkingCalendarUtilImplementation;
import co.com.ceiba.parkinglotbackend.core.entities.ParkingRates;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvalidDatesException;
import co.com.ceiba.parkinglotbackend.testdatabuilder.ParkingCalendarTestDataBuilder;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.ParkingRatesTestDataBuilder;
import co.com.ceiba.parkinglotbackend.applicationlogic.parkingattendantutils.implementations.ParkingCalculatorUtilImplementation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ParkingCalculatorUtilImplementationTests {

    private ParkingCalculatorUtil sut;

    @Spy
    private ParkingCalendarUtilImplementation spyParkingCalendarUtil;

    private ParkingRates parkingRates;
    private ParkingRatesTestDataBuilder parkingRatesTestDataBuilder;
    private LocalDateTime sunday;
    private LocalDateTime monday;

    public ParkingCalculatorUtilImplementationTests() {
        ParkingCalendarTestDataBuilder parkingCalendarTestDataBuilder = new ParkingCalendarTestDataBuilder();
        parkingRatesTestDataBuilder = new ParkingRatesTestDataBuilder();
        sunday = parkingCalendarTestDataBuilder.build();
        monday = parkingCalendarTestDataBuilder.build().plusDays(1);
    }

    @Before
    public void setUp() {
        parkingRates = parkingRatesTestDataBuilder.build();
        sut = new ParkingCalculatorUtilImplementation(spyParkingCalendarUtil);
    }

    @After
    public void tearDown() {
        sut = null;
        parkingRates = null;
    }

    @Test
    public void calculateCorrectPrice() throws InvalidDatesException {
        when(spyParkingCalendarUtil.getTodayDate()).thenReturn(monday);
        Long price = sut.calculatePrice(parkingRates, sunday);
        assertEquals("Incorrect total price", parkingRates.getDayPrice() + parkingRates.getExtraPrice(),
                price.longValue());

        when(spyParkingCalendarUtil.getTodayDate()).thenReturn(monday.plusMinutes(1));
        price = sut.calculatePrice(parkingRates, sunday);

        assertEquals("Incorrect total price", parkingRates.getDayPrice() +
                parkingRates.getHourPrice() + parkingRates.getExtraPrice(), price.longValue());

        when(spyParkingCalendarUtil.getTodayDate()).thenReturn(monday.plusMinutes(59));
        price = sut.calculatePrice(parkingRates, sunday);
        assertEquals("Incorrect total price", parkingRates.getDayPrice() +
                parkingRates.getHourPrice() + parkingRates.getExtraPrice(), price.longValue());
    }

    @Test(expected = InvalidDatesException.class)
    public void calculatePriceWithBadDates() throws InvalidDatesException {
        when(spyParkingCalendarUtil.getTodayDate()).thenReturn(sunday);
        sut.calculatePrice(parkingRates, monday);
    }

    @Test
    public void calculatePriceWithoutExtraPrice() throws InvalidDatesException {
        parkingRates = parkingRatesTestDataBuilder.withExtraPrice(0L).build();
        when(spyParkingCalendarUtil.getTodayDate()).thenReturn(monday);
        Long price = sut.calculatePrice(parkingRates, sunday);
        assertEquals("Incorrect total price", parkingRates.getDayPrice().longValue(), price.longValue());
    }

    @Test
    public void calculateFreePrice() throws InvalidDatesException {
        when(spyParkingCalendarUtil.getTodayDate()).thenReturn(sunday);
        Long price = sut.calculatePrice(parkingRates, sunday);
        assertEquals("Total price must be zero", 0, price.longValue());

        when(spyParkingCalendarUtil.getTodayDate()).thenReturn(sunday.plusMinutes(5));
        price = sut.calculatePrice(parkingRates, sunday);
        assertEquals("Total price must be zero", 0, price.longValue());
    }
}
