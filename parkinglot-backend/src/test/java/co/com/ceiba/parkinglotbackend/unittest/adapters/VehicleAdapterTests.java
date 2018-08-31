package co.com.ceiba.parkinglotbackend.unittest.adapters;

import co.com.ceiba.parkinglotbackend.adapters.VehicleAdapter;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.dtos.VehicleDTO;
import co.com.ceiba.parkinglotbackend.testdatabuilder.dtos.VehicleDTOTestDataBuilder;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.VehicleTestDataBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class VehicleAdapterTests {

    private VehicleTestDataBuilder vehicleTestDataBuilder;
    private VehicleDTO vehicleDTO;
    private Vehicle vehicle;

    @Test
    public void vehicleToDtoTest() {
        vehicleTestDataBuilder = new VehicleTestDataBuilder();
        vehicle = vehicleTestDataBuilder.build();
        vehicleDTO = VehicleAdapter.vehicleToDto(vehicle);
        Assert.assertNotNull("Error converting vehicle to dto", vehicleDTO);
        validateVehicleMapper(vehicle, vehicleDTO);
    }

    @Test
    public void dtoToVehicleTest() {
        VehicleDTO vehicleDTO = new VehicleDTOTestDataBuilder().build();
        vehicle = VehicleAdapter.dtoToVehicle(vehicleDTO);
        Assert.assertNotNull("Error converting dto to vehicle", vehicle);
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
        Assert.assertNotNull("Error converting dto page to vehicle page", vehicleDTOPage);
        Assert.assertEquals("Different sizes", vehicles.size(), vehicleDTOList.size());
        for (int i = vehicleDTOList.size() - 1; i >= 0; i--) {
            validateVehicleMapper(vehicles.get(i), vehicleDTOList.get(i));
        }
    }

    private void validateVehicleMapper(Vehicle vehicle, VehicleDTO vehicleDTO) {
        Assert.assertEquals("Cylinder capacities not equals", vehicle.getCylinderCapacity(), vehicleDTO.getCylinderCapacity());
        Assert.assertEquals("License plates not equals", vehicle.getLicensePlate(), vehicleDTO.getLicensePlate());
        Assert.assertEquals("Vehicle type names not equals", vehicle.getVehicleType().getName(), vehicleDTO.getVehicleTypeName());
    }
}
