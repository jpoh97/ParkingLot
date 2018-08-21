package co.com.ceiba.parkinglotbackend.entities;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String licensePlate;
    private Integer cylinderCapacity;
    private VehicleType vehicleType;
}
