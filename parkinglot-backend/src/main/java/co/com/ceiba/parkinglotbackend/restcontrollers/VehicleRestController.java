package co.com.ceiba.parkinglotbackend.restcontrollers;

import co.com.ceiba.parkinglotbackend.applicationlogic.ParkingAttendant;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.core.services.VehicleService;
import co.com.ceiba.parkinglotbackend.exceptions.VehicleDoesNotExistException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/vehicle")
public class VehicleRestController {

    private ParkingAttendant parkingAttendant;
    private VehicleService vehicleService;

    public VehicleRestController(VehicleService vehicleService, ParkingAttendant parkingAttendant) {
        this.vehicleService = vehicleService;
        this.parkingAttendant = parkingAttendant;
    }

    @GetMapping
    public Page<Vehicle> getAllVehicles(Pageable pageble) {
        return vehicleService.getAll(pageble);
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable String licensePlate) throws VehicleDoesNotExistException {
        Optional<Vehicle> vehicle = vehicleService.get(Optional.ofNullable(licensePlate));
        if (!vehicle.isPresent()) { throw new VehicleDoesNotExistException(); }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(vehicle.get(), headers, HttpStatus.OK);
    }

   /* @PostMapping
    public ResponseEntity<?> vehicleCheckIn(@RequestBody Vehicle vehicle) {

    }*/

}
