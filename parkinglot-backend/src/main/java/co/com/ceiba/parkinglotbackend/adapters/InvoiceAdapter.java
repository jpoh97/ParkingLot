package co.com.ceiba.parkinglotbackend.adapters;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.dtos.InvoiceDTO;
import org.modelmapper.ModelMapper;

public class InvoiceAdapter {

    public static InvoiceDTO invoiceToDTO(Invoice invoice) {
        ModelMapper modelMapper = new ModelMapper();
        InvoiceDTO invoiceDTO = modelMapper.map(invoice, InvoiceDTO.class);
        return invoiceDTO;
    }

    public static Invoice DTOToInvoice(InvoiceDTO invoiceDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Invoice invoice = modelMapper.map(invoiceDTO, Invoice.class);
        return invoice;
    }
}
