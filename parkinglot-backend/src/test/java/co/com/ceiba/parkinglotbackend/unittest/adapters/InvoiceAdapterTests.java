package co.com.ceiba.parkinglotbackend.unittest.adapters;

import co.com.ceiba.parkinglotbackend.adapters.InvoiceAdapter;
import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.dtos.InvoiceDTO;
import co.com.ceiba.parkinglotbackend.testdatabuilder.dtos.InvoiceDTOTestDataBuilder;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.InvoiceTestDataBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InvoiceAdapterTests {

    private InvoiceTestDataBuilder invoiceTestDataBuilder;
    private InvoiceDTO invoiceDTO;
    private Invoice invoice;

    @Test
    public void invoiceToDtoTest() {
        invoiceTestDataBuilder = new InvoiceTestDataBuilder();
        invoice = invoiceTestDataBuilder.build();
        invoiceDTO = InvoiceAdapter.invoiceToDto(invoice);
        Assert.assertNotNull("Error converting invoice to dto", invoiceDTO);
        validateInvoiceMapper(invoice, invoiceDTO);
    }

    @Test
    public void dtoToInvoiceTest() {
        invoiceDTO = new InvoiceDTOTestDataBuilder().build();
        invoice = InvoiceAdapter.dtoToInvoice(invoiceDTO);
        Assert.assertNotNull("Error converting invoice to dto", invoice);
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
        Assert.assertNotNull("Error converting dto page to invoice page", invoiceDTOPage);
        Assert.assertEquals("Different sizes", invoices.size(), invoiceDTOList.size());
        for (int i = invoiceDTOList.size() - 1; i >= 0; i--) {
            validateInvoiceMapper(invoices.get(i), invoiceDTOList.get(i));
        }
    }

    private void validateInvoiceMapper(Invoice invoice, InvoiceDTO invoiceDTO) {
        Assert.assertEquals("License plates not equals", invoice.getVehicle().getLicensePlate(), invoiceDTO.getVehicleLicensePlate());
        Assert.assertEquals("Entry dates not equals", invoice.getEntryDate(), invoiceDTO.getEntryDate());
        Assert.assertEquals("Departure dates not equals", invoice.getDepartureDate(), invoiceDTO.getDepartureDate());
        Assert.assertEquals("Prices not equals", invoice.getPrice(), invoiceDTO.getPrice());
        Assert.assertEquals("Vehicle type names not equals", invoice.getVehicle().getVehicleType().getName(), invoiceDTO.getVehicleTypeName());
    }
}
