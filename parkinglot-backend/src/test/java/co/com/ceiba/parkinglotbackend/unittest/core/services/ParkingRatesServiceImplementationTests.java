package co.com.ceiba.parkinglotbackend.unittest.core.services;

import co.com.ceiba.parkinglotbackend.core.entities.ParkingRates;
import co.com.ceiba.parkinglotbackend.core.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.core.repositories.ParkingRatesRepository;
import co.com.ceiba.parkinglotbackend.core.services.ParkingRatesService;
import co.com.ceiba.parkinglotbackend.core.services.implementations.ParkingRatesServiceImplementation;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.ParkingRatesDataException;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.ParkingRatesTestDataBuilder;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.VehicleTypeTestDataBuilder;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.VehicleTestDataBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ParkingRatesServiceImplementationTests {

    @Mock
    private ParkingRatesRepository mockParkingRatesRepository;

    private ParkingRatesService sut;
    private ParkingRates parkingRates;
    private ParkingRatesTestDataBuilder parkingRatesTestDataBuilder;

    public ParkingRatesServiceImplementationTests() {
        parkingRatesTestDataBuilder = new ParkingRatesTestDataBuilder();
    }

    @Before
    public void setUp() {
        parkingRates = parkingRatesTestDataBuilder.build();
        sut = new ParkingRatesServiceImplementation(mockParkingRatesRepository);
    }

    @After
    public void tearDown() {
        sut = null;
        parkingRates = null;
    }

    @Test
    public void getAllTest() {
        // arrange
        List<ParkingRates> parkingRatesList = getParkingRatesList();
        when(mockParkingRatesRepository.findAll()).thenReturn(parkingRatesList);

        // act
        Stream<ParkingRates> parkingRatesStream = sut.getAll();

        // assert
        assertNotNull("Stream return is null", parkingRatesStream);
        assertEquals("Sizes not equals", parkingRatesList.size(), parkingRatesStream.count());
    }

    @Test
    public void getCurrentParkingRatesTest() throws ParkingRatesDataException {
        // arrange
        List<ParkingRates> parkingRatesList = getParkingRatesList();
        when(mockParkingRatesRepository.findAllByActive(true)).thenReturn(parkingRatesList);

        // act
        List<ParkingRates> parkingRatesResponse = sut.getCurrentParkingRates(true);

        // assert
        assertNotNull("Parking rates list is null", parkingRatesResponse);
        assertEquals("Sizes not equals", parkingRatesList.size(), parkingRatesResponse.size());
    }

    @Test(expected = ParkingRatesDataException.class)
    public void getCurrentParkingRatesWithoutActiveParamTest() throws ParkingRatesDataException {
        sut.getCurrentParkingRates(null);
    }

    @Test
    public void getCurrentParkingRatesForTest() throws ParkingRatesDataException {
        List<ParkingRates> parkingRatesList = getParkingRatesList();
        when(mockParkingRatesRepository.findAllByActive(true)).thenReturn(parkingRatesList);

        VehicleType vehicleType = getVehicleType(1);

        parkingRates = sut.getCurrentParkingRatesFor(vehicleType,
                new VehicleTestDataBuilder().build().getCylinderCapacity());

        assertNotNull("There is not parking rates", parkingRates);
    }

    @Test
    public void getCurrentParkingRatesForUnmatchedAParkingRatesTest() throws ParkingRatesDataException {
        List<ParkingRates> parkingRatesList = getParkingRatesList();
        when(mockParkingRatesRepository.findAllByActive(true)).thenReturn(parkingRatesList);

        VehicleType vehicleType = getVehicleType(2);

        parkingRates = sut.getCurrentParkingRatesFor(vehicleType,
                new VehicleTestDataBuilder().build().getCylinderCapacity());

        assertNull("There is at least one parking rates", parkingRates);
    }

    @Test(expected = ParkingRatesDataException.class)
    public void getCurrentParkingRatesForWithoutParamsTest() throws ParkingRatesDataException {
        sut.getCurrentParkingRatesFor(null, Optional.empty());
    }

    private List<ParkingRates> getParkingRatesList() {
        List<ParkingRates> parkingRatesList = new ArrayList<>();
        parkingRates.setVehicleType(getVehicleType(2));
        parkingRatesList.add(parkingRates);
        parkingRates = parkingRatesTestDataBuilder.withExtraPrice(0L).build();
        parkingRates.setVehicleType(getVehicleType(1));
        parkingRatesList.add(parkingRates);
        return parkingRatesList;
    }

    private VehicleType getVehicleType(Integer id) {
        VehicleType vehicleType = new VehicleTypeTestDataBuilder().build();
        vehicleType.setId(id);
        return vehicleType;
    }
}
