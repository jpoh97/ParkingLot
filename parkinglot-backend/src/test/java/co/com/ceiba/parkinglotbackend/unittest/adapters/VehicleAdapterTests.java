package co.com.ceiba.parkinglotbackend.unittest.adapters;

import co.com.ceiba.parkinglotbackend.adapters.VehicleAdapter;
import co.com.ceiba.parkinglotbackend.persistence.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.dtos.VehicleDTO;
import co.com.ceiba.parkinglotbackend.testdatabuilder.dtos.VehicleDTOTestDataBuilder;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.VehicleTestDataBuilder;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class VehicleAdapterTests {

    private VehicleTestDataBuilder vehicleTestDataBuilder;
    private Vehicle vehicle;

    @Test
    public void vehicleToDtoTest() {
        vehicleTestDataBuilder = new VehicleTestDataBuilder();
        vehicle = vehicleTestDataBuilder.build();
        VehicleDTO vehicleDTO = VehicleAdapter.vehicleToDto(vehicle);
        assertNotNull("Error converting vehicle to dto", vehicleDTO);
        validateVehicleMapper(vehicle, vehicleDTO);
    }

    @Test
    public void dtoToVehicleTest() {
        VehicleDTO vehicleDTO = new VehicleDTOTestDataBuilder().build();
        vehicle = VehicleAdapter.dtoToVehicle(vehicleDTO);
        assertNotNull("Error converting dto to vehicle", vehicle);
        validateVehicleMapper(vehicle, vehicleDTO);
    }

    @Test
    public void vehicleListToDtoList() {
        // arrange
        List<Vehicle> vehicles = new ArrayList<>();
        vehicleTestDataBuilder = new VehicleTestDataBuilder();
        vehicle = vehicleTestDataBuilder.build();
        vehicles.add(vehicle);
        vehicle = vehicleTestDataBuilder.withCylinderCapacity(5).build();
        vehicles.add(vehicle);
        Page<Vehicle> vehiclePage = new PageImpl<>(vehicles);

        // act
        Page<VehicleDTO> vehicleDTOPage = VehicleAdapter.vehicleListToDtoList(vehiclePage);
        List<VehicleDTO> vehicleDTOList = vehicleDTOPage.getContent();

        // assert
        assertNotNull("Error converting dto page to vehicle page", vehicleDTOPage);
        assertEquals("Different sizes", vehicles.size(), vehicleDTOList.size());
        for (int i = vehicleDTOList.size() - 1; i >= 0; i--) {
            validateVehicleMapper(vehicles.get(i), vehicleDTOList.get(i));
        }
    }

    private void validateVehicleMapper(Vehicle vehicle, VehicleDTO vehicleDTO) {
        assertEquals("Cylinder capacities not equals", vehicle.getCylinderCapacity(), vehicleDTO.getCylinderCapacity());
        assertEquals("License plates not equals", vehicle.getLicensePlate(), vehicleDTO.getLicensePlate());
        assertEquals("Vehicle type names not equals", vehicle.getVehicleType().getName(), vehicleDTO.getVehicleTypeName());
    }
}
