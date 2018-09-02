package co.com.ceiba.parkinglotbackend.unittest.adapters;

import co.com.ceiba.parkinglotbackend.adapters.InvoiceAdapter;
import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.dtos.InvoiceDTO;
import co.com.ceiba.parkinglotbackend.testdatabuilder.dtos.InvoiceDTOTestDataBuilder;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.InvoiceTestDataBuilder;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class InvoiceAdapterTests {

    private InvoiceTestDataBuilder invoiceTestDataBuilder;
    private InvoiceDTO invoiceDTO;
    private Invoice invoice;

    @Test
    public void invoiceToDtoTest() {
        invoiceTestDataBuilder = new InvoiceTestDataBuilder();
        invoice = invoiceTestDataBuilder.build();
        invoiceDTO = InvoiceAdapter.invoiceToDto(invoice);
        assertNotNull("Error converting invoice to dto", invoiceDTO);
        validateInvoiceMapper(invoice, invoiceDTO);
    }

    @Test
    public void dtoToInvoiceTest() {
        invoiceDTO = new InvoiceDTOTestDataBuilder().build();
        invoice = InvoiceAdapter.dtoToInvoice(invoiceDTO);
        assertNotNull("Error converting invoice to dto", invoice);
        validateInvoiceMapper(invoice, invoiceDTO);
    }

    @Test
    public void invoiceListToDtoListTest() {
        // arrange
        List<Invoice> invoices = new ArrayList<>();
        invoiceTestDataBuilder = new InvoiceTestDataBuilder();
        invoice = invoiceTestDataBuilder.build();
        invoices.add(invoice);
        invoice = invoiceTestDataBuilder.withEntryDate(LocalDateTime.now().minusMonths(1)).build();
        invoices.add(invoice);
        Page<Invoice> invoicePage = new PageImpl<>(invoices);

        //act
        Page<InvoiceDTO> invoiceDTOPage = InvoiceAdapter.invoiceListToDtoList(invoicePage);
        List<InvoiceDTO> invoiceDTOList = invoiceDTOPage.getContent();

        // assert
        assertNotNull("Error converting dto page to invoice page", invoiceDTOPage);
        assertEquals("Different sizes", invoices.size(), invoiceDTOList.size());
        for (int i = invoiceDTOList.size() - 1; i >= 0; i--) {
            validateInvoiceMapper(invoices.get(i), invoiceDTOList.get(i));
        }
    }

    private void validateInvoiceMapper(Invoice invoice, InvoiceDTO invoiceDTO) {
        assertEquals("License plates not equals", invoice.getVehicle().getLicensePlate(), invoiceDTO.getVehicleLicensePlate());
        assertEquals("Entry dates not equals", invoice.getEntryDate(), invoiceDTO.getEntryDate());
        assertEquals("Departure dates not equals", invoice.getDepartureDate(), invoiceDTO.getDepartureDate());
        assertEquals("Prices not equals", invoice.getPrice(), invoiceDTO.getPrice());
        assertEquals("Vehicle type names not equals", invoice.getVehicle().getVehicleType().getName(), invoiceDTO.getVehicleTypeName());
    }
}
