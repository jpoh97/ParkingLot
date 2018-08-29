package co.com.ceiba.parkinglotbackend.restcontrollers;

import co.com.ceiba.parkinglotbackend.adapters.InvoiceAdapter;
import co.com.ceiba.parkinglotbackend.applicationlogic.ParkingAttendant;
import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.core.services.VehicleService;
import co.com.ceiba.parkinglotbackend.dtos.InvoiceDTO;
import co.com.ceiba.parkinglotbackend.dtos.VehicleDTO;
import co.com.ceiba.parkinglotbackend.exceptions.BaseException;
import co.com.ceiba.parkinglotbackend.exceptions.Implementations.VehicleDoesNotExistException;
import co.com.ceiba.parkinglotbackend.adapters.VehicleAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("vehicle")
public class VehicleRestController {

    private ParkingAttendant parkingAttendant;
    private VehicleService vehicleService;

    public VehicleRestController(VehicleService vehicleService, ParkingAttendant parkingAttendant) {
        this.vehicleService = vehicleService;
        this.parkingAttendant = parkingAttendant;
    }

    @GetMapping
    public Page<VehicleDTO> listVehicles(Pageable pageable) {
        return VehicleAdapter.vehicleListToDTOList(vehicleService.getAll(pageable));
    }

    @GetMapping("{licensePlate}")
    public ResponseEntity<VehicleDTO> getVehicle(@PathVariable String licensePlate) throws VehicleDoesNotExistException {
        Optional<Vehicle> vehicle = vehicleService.get(Optional.ofNullable(licensePlate));
        if (!vehicle.isPresent()) { throw new VehicleDoesNotExistException(); }
        VehicleDTO vehicleDTO = VehicleAdapter.vehicleToDTO(vehicle.get());
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(vehicleDTO, headers, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<InvoiceDTO> vehicleCheckIn(@RequestBody VehicleDTO vehicleDTO) throws BaseException {
        Vehicle vehicle = VehicleAdapter.DTOToVehicle(vehicleDTO);
        Invoice invoice = parkingAttendant.vehicleCheckIn(vehicle, LocalDateTime.now());
        InvoiceDTO invoiceDTO = InvoiceAdapter.invoiceToDTO(invoice);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(invoiceDTO, headers, HttpStatus.OK);
    }

    @PutMapping("{licensePlate}")
    public ResponseEntity<InvoiceDTO> vehicleCheckOut(@PathVariable String licensePlate) throws BaseException {
        Invoice invoice = parkingAttendant.vehicleCheckOut(licensePlate, LocalDateTime.now());
        InvoiceDTO invoiceDTO = InvoiceAdapter.invoiceToDTO(invoice);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(invoiceDTO, headers, HttpStatus.OK);
    }
}
