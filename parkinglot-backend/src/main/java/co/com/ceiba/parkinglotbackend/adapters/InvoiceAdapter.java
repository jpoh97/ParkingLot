package co.com.ceiba.parkinglotbackend.adapters;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.dtos.InvoiceDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public final class InvoiceAdapter {

    private InvoiceAdapter() {
    }

    public static InvoiceDTO invoiceToDto(Invoice invoice) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(invoice, InvoiceDTO.class);
    }

    public static Invoice dtoToInvoice(InvoiceDTO invoiceDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(invoiceDTO, Invoice.class);
    }

    public static Page<InvoiceDTO> invoiceListToDtoList(Page<Invoice> invoices) {
        return invoices.map(InvoiceAdapter::invoiceToDto);
    }
}
