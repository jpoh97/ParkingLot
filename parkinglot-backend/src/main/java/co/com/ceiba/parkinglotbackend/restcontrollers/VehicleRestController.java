package co.com.ceiba.parkinglotbackend.restcontrollers;

import co.com.ceiba.parkinglotbackend.adapters.InvoiceAdapter;
import co.com.ceiba.parkinglotbackend.applicationlogic.ParkingAttendant;
import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.core.entities.Vehicle;
import co.com.ceiba.parkinglotbackend.core.services.VehicleService;
import co.com.ceiba.parkinglotbackend.dtos.InvoiceDTO;
import co.com.ceiba.parkinglotbackend.dtos.VehicleDTO;
import co.com.ceiba.parkinglotbackend.exceptions.BaseException;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleDataException;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.VehicleDoesNotExistException;
import co.com.ceiba.parkinglotbackend.adapters.VehicleAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("vehicle")
public class VehicleRestController {

    private final ThreadLocal<ParkingAttendant> parkingAttendant = new ThreadLocal<>();
    private final ThreadLocal<VehicleService> vehicleService = new ThreadLocal<>();

    public VehicleRestController(VehicleService vehicleService, ParkingAttendant parkingAttendant) {
        this.vehicleService.set(vehicleService);
        this.parkingAttendant.set(parkingAttendant);
    }

    @GetMapping
    public Page<VehicleDTO> listVehicles(Pageable pageable) throws VehicleDataException {
        return VehicleAdapter.vehicleListToDtoList(vehicleService.get().getAll(pageable));
    }

    @GetMapping("{licensePlate}")
    public ResponseEntity<VehicleDTO> getVehicle(@PathVariable String licensePlate) throws VehicleDoesNotExistException {
        Optional<Vehicle> vehicle = vehicleService.get().get(Optional.ofNullable(licensePlate));
        VehicleDTO vehicleDTO = VehicleAdapter.vehicleToDto(vehicle.get());
        return new ResponseEntity<>(vehicleDTO, getHeaders(), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<InvoiceDTO> vehicleCheckIn(@RequestBody VehicleDTO vehicleDTO) throws BaseException {
        Vehicle vehicle = VehicleAdapter.dtoToVehicle(vehicleDTO);
        Invoice invoice = parkingAttendant.get().vehicleCheckIn(vehicle);
        InvoiceDTO invoiceDTO = InvoiceAdapter.invoiceToDto(invoice);
        return new ResponseEntity<>(invoiceDTO, getHeaders(), HttpStatus.CREATED);
    }

    @PutMapping("{licensePlate}")
    public ResponseEntity<InvoiceDTO> vehicleCheckOut(@PathVariable String licensePlate) throws BaseException {
        Invoice invoice = parkingAttendant.get().vehicleCheckOut(licensePlate);
        InvoiceDTO invoiceDTO = InvoiceAdapter.invoiceToDto(invoice);
        return new ResponseEntity<>(invoiceDTO, getHeaders(), HttpStatus.OK);
    }

    private HttpHeaders getHeaders() {
        return new HttpHeaders();
    }
}
