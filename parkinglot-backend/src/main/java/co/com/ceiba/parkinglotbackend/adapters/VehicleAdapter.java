package co.com.ceiba.parkinglotbackend.adapters;

import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.dtos.VehicleDTO;
import org.modelmapper.ModelMapper;

public class VehicleAdapter {

    public static VehicleDTO vehicleToDTO(Vehicle vehicle) {
        ModelMapper modelMapper = new ModelMapper();
        VehicleDTO vehicleDTO = modelMapper.map(vehicle, VehicleDTO.class);
        return vehicleDTO;
    }

    public static Vehicle DTOToVehicle(VehicleDTO vehicleDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class);
        return vehicle;
    }
}
