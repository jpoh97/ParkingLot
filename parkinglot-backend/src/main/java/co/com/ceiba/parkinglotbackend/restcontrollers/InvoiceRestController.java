package co.com.ceiba.parkinglotbackend.restcontrollers;

import co.com.ceiba.parkinglotbackend.adapters.InvoiceAdapter;
import co.com.ceiba.parkinglotbackend.core.services.InvoiceService;
import co.com.ceiba.parkinglotbackend.dtos.InvoiceDTO;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvoiceDataException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("invoice")
public class InvoiceRestController {

    private final InvoiceService invoiceService;

    public InvoiceRestController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("history")
    public Page<InvoiceDTO> listAllInvoices(Pageable pageable) throws InvoiceDataException {
        return InvoiceAdapter.invoiceListToDtoList(invoiceService.getAll(pageable));
    }

    @GetMapping
    public Page<InvoiceDTO> listVehiclesInParking(Pageable pageable) throws InvoiceDataException {
        return InvoiceAdapter.invoiceListToDtoList(invoiceService.getAllInParking(pageable));
    }
}
