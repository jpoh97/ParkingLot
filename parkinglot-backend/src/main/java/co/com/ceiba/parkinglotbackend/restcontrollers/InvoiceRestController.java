package co.com.ceiba.parkinglotbackend.restcontrollers;

import co.com.ceiba.parkinglotbackend.adapters.InvoiceAdapter;
import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.core.services.InvoiceService;
import co.com.ceiba.parkinglotbackend.dtos.InvoiceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("invoice")
public class InvoiceRestController {

    private InvoiceService invoiceService;

    public InvoiceRestController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("history")
    public Page<InvoiceDTO> listAllInvoices(Pageable pageable) {
        return InvoiceAdapter.invoiceListToDTOList(invoiceService.getAll(pageable));
    }

    @GetMapping
    public Page<InvoiceDTO> listVehiclesInParking(Pageable pageable) {
        return InvoiceAdapter.invoiceListToDTOList(invoiceService.getAllInParking(pageable));
    }

}
