package co.com.ceiba.parkinglotbackend.unittest.core.services;

import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.core.repositories.VehicleRepository;
import co.com.ceiba.parkinglotbackend.core.services.VehicleService;
import co.com.ceiba.parkinglotbackend.core.services.VehicleTypeService;
import co.com.ceiba.parkinglotbackend.core.services.implementations.VehicleServiceImplementation;
import co.com.ceiba.parkinglotbackend.exceptions.BaseException;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleDataException;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleDoesNotExistException;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.VehicleTestDataBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class VehicleServiceImplementationTests {

    @Mock
    private VehicleRepository mockVehicleRepository;

    @Mock
    private VehicleTypeService mockVehicleTypeService;

    private VehicleService sut;
    private Vehicle vehicle;
    private VehicleTestDataBuilder vehicleTestDataBuilder;

    public VehicleServiceImplementationTests() {
        vehicleTestDataBuilder = new VehicleTestDataBuilder();
    }

    @Before
    public void setUp() {
        vehicle = vehicleTestDataBuilder.build();
        sut = new VehicleServiceImplementation(mockVehicleRepository, mockVehicleTypeService);
    }

    @After
    public void tearDown() {
        sut = null;
        vehicle = null;
    }

    @Test
    public void getAllTest() throws VehicleDataException {
        // arrange
        List<Vehicle> vehicles = getListOfVehicles();
        Page<Vehicle> vehiclePageParam = new PageImpl<>(vehicles);

        // act
        when(mockVehicleRepository.findAll(PageRequest.of(1, 2))).thenReturn(vehiclePageParam);
        Page<Vehicle> vehiclePage = sut.getAll(PageRequest.of(1, 2));

        // assert
        assertNotNull("Vehicle page response is null", vehiclePage);
        assertEquals("Sizes not equals", vehicles.size(), vehiclePage.getTotalElements());
    }

    @Test(expected = VehicleDataException.class)
    public void getAllWithoutPageableTest() throws VehicleDataException {
        sut.getAll(null);
    }

    @Test
    public void getCorrectVehicle() throws VehicleDoesNotExistException {
        when(mockVehicleRepository.findByLicensePlate(anyString())).thenReturn(Optional.ofNullable(vehicle));

        Optional<Vehicle> vehicleResponse = sut.get(Optional.ofNullable(vehicle.getLicensePlate()));
        assertTrue("Get func returned null", vehicleResponse.isPresent());
        validateVehiclesEquals(vehicle, vehicleResponse.get());
    }

    @Test(expected = VehicleDoesNotExistException.class)
    public void getWithoutLicensePlate() throws VehicleDoesNotExistException {
        sut.get(Optional.empty());
    }


    @Test(expected = VehicleDoesNotExistException.class)
    public void getNonexistentVehicle() throws VehicleDoesNotExistException {
        when(mockVehicleRepository.findByLicensePlate(anyString())).thenReturn(Optional.empty());
        sut.get(Optional.ofNullable(vehicle.getLicensePlate()));
    }

    @Test
    public void addNewVehicle() throws VehicleDataException {
        when(mockVehicleRepository.findByLicensePlate(anyString())).thenReturn(Optional.empty());
        when(mockVehicleRepository.save(any())).thenReturn(vehicle);

        Optional<Vehicle> newVehicle = sut.add(Optional.ofNullable(vehicle));
        assertTrue("Vehicle response is null", newVehicle.isPresent());
        validateVehiclesEquals(vehicle, newVehicle.get());
    }

    @Test
    public void addExistingVehicle() throws VehicleDataException {
        // arrange
        vehicle = vehicleTestDataBuilder.withLicensePlate(vehicle.getLicensePlate().toUpperCase()).build();
        when(mockVehicleRepository.findByLicensePlate(anyString())).thenReturn(Optional.ofNullable(vehicle));
        when(mockVehicleRepository.save(any())).thenReturn(vehicle);
        vehicle = vehicleTestDataBuilder.withCylinderCapacity(100).build();

        // act
        Optional<Vehicle> newVehicle = sut.add(Optional.ofNullable(vehicle));

        // assert
        assertTrue("Vehicle response is null", newVehicle.isPresent());
        validateVehiclesEquals(vehicle, newVehicle.get());
    }

    @Test(expected = VehicleDataException.class)
    public void addWithoutParam() throws VehicleDataException {
        sut.add(Optional.empty());
    }

    @Test
    public void getNewVehicleTest() throws BaseException {
        when(mockVehicleTypeService.getCurrentVehicleType(anyString())).thenReturn(Optional.ofNullable(vehicle.getVehicleType()));

        Vehicle newVehicle = sut.getNewVehicle(vehicle.getLicensePlate(), vehicle.getVehicleType().getName(),
                vehicle.getCylinderCapacity().get());

        // always return license plate in upper case
        newVehicle.setLicensePlate(newVehicle.getLicensePlate().toLowerCase());
        assertNotNull("New vehicle is null", newVehicle);
        validateVehiclesEquals(vehicle, newVehicle);
    }

    @Test(expected = VehicleDataException.class)
    public void getNewVehicleWithoutParams() throws BaseException {
        sut.getNewVehicle(null, null, null);
    }

    private List<Vehicle> getListOfVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle);
        vehicle = vehicleTestDataBuilder.withCreationDate(LocalDateTime.now().minusWeeks(10)).build();
        vehicles.add(vehicle);
        return vehicles;
    }

    private void validateVehiclesEquals(Vehicle vehicle, Vehicle vehicleResponse) {
        assertEquals("License plates are not equals", vehicle.getLicensePlate(), vehicleResponse.getLicensePlate());
        assertEquals("Vehicle type name are not equals", vehicle.getVehicleType().getName(),
                vehicleResponse.getVehicleType().getName());
        assertEquals("CC are not equals", vehicle.getCylinderCapacity(), vehicleResponse.getCylinderCapacity());
    }
}
