package co.com.ceiba.parkinglotbackend.adapters;

import co.com.ceiba.parkinglotbackend.persistence.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.dtos.VehicleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public final class VehicleAdapter {

    private VehicleAdapter() {
    }

    public static VehicleDTO vehicleToDto(Vehicle vehicle) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(vehicle, VehicleDTO.class);
    }

    public static Vehicle dtoToVehicle(VehicleDTO vehicleDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(vehicleDTO, Vehicle.class);
    }

    public static Page<VehicleDTO> vehicleListToDtoList(Page<Vehicle> vehicles) {
        return vehicles.map(VehicleAdapter::vehicleToDto);
    }
}
