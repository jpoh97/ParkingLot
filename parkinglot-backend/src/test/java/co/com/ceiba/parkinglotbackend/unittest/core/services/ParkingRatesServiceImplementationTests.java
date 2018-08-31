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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ParkingRatesServiceImplementationTests {

    @Mock
    private ParkingRatesRepository mockParkingRatesRepository;

    private ParkingRatesService sut;
    private ParkingRates parkingRates;
    private ParkingRatesTestDataBuilder parkingRatesTestDataBuilder;

    @Before
    public void setUp() {
        parkingRatesTestDataBuilder = new ParkingRatesTestDataBuilder();
        parkingRates = parkingRatesTestDataBuilder.build();
    }

    @After
    public void tearDown() {
        parkingRates = null;
        parkingRatesTestDataBuilder = null;
    }

    @Test
    public void getAllTest() {
        // arrange
        List<ParkingRates> parkingRatesList = getParkingRatesList();
        when(mockParkingRatesRepository.findAll()).thenReturn(parkingRatesList);

        // act
        sut = new ParkingRatesServiceImplementation(mockParkingRatesRepository);
        Stream<ParkingRates> parkingRatesStream = sut.getAll();

        // assert
        Assert.assertNotNull("Stream return is null", parkingRatesStream);
        Assert.assertEquals("Sizes not equals", parkingRatesList.size(), parkingRatesStream.count());
    }

    @Test
    public void getCurrentParkingRatesTest() throws ParkingRatesDataException {
        // arrange
        List<ParkingRates> parkingRatesList = getParkingRatesList();
        when(mockParkingRatesRepository.findAllByActive(true)).thenReturn(parkingRatesList);

        // act
        sut = new ParkingRatesServiceImplementation(mockParkingRatesRepository);
        List<ParkingRates> parkingRatesResponse = sut.getCurrentParkingRates(true);

        // assert
        Assert.assertNotNull("Parking rates list is null", parkingRatesResponse);
        Assert.assertEquals("Sizes not equals", parkingRatesList.size(), parkingRatesResponse.size());
    }

    @Test(expected = ParkingRatesDataException.class)
    public void getCurrentParkingRatesWithoutActiveParamTest() throws ParkingRatesDataException {
        sut = new ParkingRatesServiceImplementation(mockParkingRatesRepository);
        sut.getCurrentParkingRates(null);
    }

    @Test
    public void getCurrentParkingRatesForTest() throws ParkingRatesDataException {
        List<ParkingRates> parkingRatesList = getParkingRatesList();
        when(mockParkingRatesRepository.findAllByActive(true)).thenReturn(parkingRatesList);

        VehicleType vehicleType = getVehicleType(1);

        sut = new ParkingRatesServiceImplementation(mockParkingRatesRepository);
        parkingRates = sut.getCurrentParkingRatesFor(vehicleType,
                new VehicleTestDataBuilder().build().getCylinderCapacity());

        // assert
        Assert.assertNotNull("There is not parking rates", parkingRates);
    }

    @Test
    public void getCurrentParkingRatesForUnmatchedAParkingRatesTest() throws ParkingRatesDataException {
        List<ParkingRates> parkingRatesList = getParkingRatesList();
        when(mockParkingRatesRepository.findAllByActive(true)).thenReturn(parkingRatesList);

        VehicleType vehicleType = getVehicleType(2);

        sut = new ParkingRatesServiceImplementation(mockParkingRatesRepository);
        parkingRates = sut.getCurrentParkingRatesFor(vehicleType,
                new VehicleTestDataBuilder().build().getCylinderCapacity());

        // assert
        Assert.assertNull("There is at least one parking rates", parkingRates);
    }

    @Test(expected = ParkingRatesDataException.class)
    public void getCurrentParkingRatesForWithoutParamsTest() throws ParkingRatesDataException {
        sut = new ParkingRatesServiceImplementation(mockParkingRatesRepository);
        sut.getCurrentParkingRatesFor(null, null);
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
