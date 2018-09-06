package co.com.ceiba.parkinglotbackend.unittest.core.services;

import co.com.ceiba.parkinglotbackend.core.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.core.repositories.VehicleTypeRepository;
import co.com.ceiba.parkinglotbackend.core.services.VehicleTypeService;
import co.com.ceiba.parkinglotbackend.core.services.implementations.VehicleTypeServiceImplementation;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleTypeDataException;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.VehicleTypeTestDataBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringJUnit4ClassRunner.class)
public class VehicleTypeServiceImplementationTests {

    @Mock
    private VehicleTypeRepository mockVehicleTypeRepository;

    private VehicleTypeService sut;
    private VehicleType vehicleType;
    private VehicleTypeTestDataBuilder vehicleTypeTestDataBuilder;

    public VehicleTypeServiceImplementationTests() {
        vehicleTypeTestDataBuilder = new VehicleTypeTestDataBuilder();
    }

    @Before
    public void setUp() {
        vehicleType = vehicleTypeTestDataBuilder.build();
        sut = new VehicleTypeServiceImplementation(mockVehicleTypeRepository);
    }

    @After
    public void tearDown() {
        sut = null;
        vehicleType = null;
    }

    @Test
    public void getCurrentVehicleTypeTest() throws VehicleTypeDataException {
        when(mockVehicleTypeRepository.findByName(anyString())).thenReturn(Optional.ofNullable(vehicleType));
        Optional<VehicleType> vehicleTypeResponse = sut.getCurrentVehicleType(vehicleType.getName());
        validateVehicleTypes(vehicleType, vehicleTypeResponse);
    }

    @Test(expected = VehicleTypeDataException.class)
    public void getCurrentVehicleTypeWithoutNameParam() throws VehicleTypeDataException {
        sut.getCurrentVehicleType(null);
    }

    private void validateVehicleTypes(VehicleType vehicleType, Optional<VehicleType> vehicleTypeResponse) {
        assertTrue("Vehicle type is null", vehicleTypeResponse.isPresent());
        assertEquals("Names not equals", vehicleType.getName(), vehicleTypeResponse.get().getName());
        assertEquals("Dates not equals", vehicleType.getCreationDate(), vehicleTypeResponse.get().getCreationDate());
        assertEquals("Total space not equals", vehicleType.getTotalPlaces(), vehicleTypeResponse.get().getTotalPlaces());
    }
}
