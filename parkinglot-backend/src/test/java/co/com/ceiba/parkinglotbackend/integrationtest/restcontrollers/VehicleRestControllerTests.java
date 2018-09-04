package co.com.ceiba.parkinglotbackend.integrationtest.restcontrollers;

import co.com.ceiba.parkinglotbackend.applicationlogic.ParkingAttendant;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.core.services.VehicleService;
import co.com.ceiba.parkinglotbackend.core.services.VehicleTypeService;
import co.com.ceiba.parkinglotbackend.dtos.VehicleDTO;
import co.com.ceiba.parkinglotbackend.exceptions.BaseException;
import co.com.ceiba.parkinglotbackend.restcontrollers.VehicleRestController;
import co.com.ceiba.parkinglotbackend.testdatabuilder.dtos.VehicleDTOTestDataBuilder;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.VehicleTestDataBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class VehicleRestControllerTests {

    private VehicleRestController sut;

    @Autowired private ParkingAttendant parkingAttendant;
    @Autowired private VehicleService vehicleService;
    @Autowired private VehicleTypeService vehicleTypeService;

    private Vehicle vehicle;
    private VehicleTestDataBuilder vehicleTestDataBuilder;
    private VehicleDTO vehicleDTO;
    private VehicleDTOTestDataBuilder vehicleDTOTestDataBuilder;

    @Before
    public void setUp() {
        vehicleTestDataBuilder = new VehicleTestDataBuilder();
        vehicleDTOTestDataBuilder = new VehicleDTOTestDataBuilder();
        vehicle = vehicleTestDataBuilder.build();
        vehicleDTO = vehicleDTOTestDataBuilder.build();
        sut = new VehicleRestController(vehicleService, parkingAttendant);
    }

    @After
    public void tearDown() {
        sut = null;
        vehicle = null;
        vehicleTestDataBuilder = null;
    }

    @Test
    public void listVehiclesTest() throws BaseException {
        vehicle.setVehicleType(vehicleTypeService.getCurrentVehicleType(vehicle.getVehicleType().getName()).get());
        vehicleService.add(Optional.ofNullable(vehicle));
        Page<VehicleDTO> vehicleDTOPage = sut.listVehicles(PageRequest.of(1, 2));
        assertNotNull("Vehicle list retrieved is a null object", vehicleDTOPage);
        assertEquals("Vehicle size is different", 1, vehicleDTOPage.getTotalElements());
    }

    @Test
    public void getVehicleTest() throws BaseException {
        vehicle.setVehicleType(vehicleTypeService.getCurrentVehicleType(vehicle.getVehicleType().getName()).get());
        vehicleService.add(Optional.ofNullable(vehicle));
        assertNotNull("Vehicle retrieved is a null object", sut.getVehicle(vehicle.getLicensePlate()));
    }

    @Test
    public void vehicleCheckInTest() throws BaseException {
        assertNotNull("Invoice retrieved is a null object", sut.vehicleCheckIn(vehicleDTO));
    }

    @Test
    public void vehicleCheckOutTest() throws BaseException {
        sut.vehicleCheckIn(vehicleDTO);
        assertNotNull("Invoice retrieved is a null object", sut.vehicleCheckOut(vehicleDTO.getLicensePlate()));
    }
}
