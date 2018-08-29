package co.com.ceiba.parkinglotbackend.adapters;

import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.dtos.VehicleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public class VehicleAdapter {

    public static VehicleDTO vehicleToDTO(Vehicle vehicle) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(vehicle, VehicleDTO.class);
    }

    public static Vehicle DTOToVehicle(VehicleDTO vehicleDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(vehicleDTO, Vehicle.class);
    }

    public static Page<VehicleDTO> vehicleListToDTOList(Page<Vehicle> vehicles) {
        return vehicles.map(VehicleAdapter::vehicleToDTO);
    }
}
