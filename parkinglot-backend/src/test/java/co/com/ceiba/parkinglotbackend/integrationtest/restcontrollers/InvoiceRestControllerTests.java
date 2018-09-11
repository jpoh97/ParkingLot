package co.com.ceiba.parkinglotbackend.integrationtest.restcontrollers;

import co.com.ceiba.parkinglotbackend.businesslogic.parkingattendant.ParkingAttendant;
import co.com.ceiba.parkinglotbackend.persistence.entities.Invoice;
import co.com.ceiba.parkinglotbackend.persistence.entities.VehicleType;
import co.com.ceiba.parkinglotbackend.businesslogic.InvoiceService;
import co.com.ceiba.parkinglotbackend.businesslogic.VehicleTypeService;
import co.com.ceiba.parkinglotbackend.dtos.InvoiceDTO;
import co.com.ceiba.parkinglotbackend.exceptions.BaseException;
import co.com.ceiba.parkinglotbackend.restcontrollers.InvoiceRestController;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.InvoiceTestDataBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class InvoiceRestControllerTests {

    private InvoiceRestController sut;

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private VehicleTypeService vehicleTypeService;
    @Autowired
    private ParkingAttendant parkingAttendant;

    private Invoice invoice;
    private InvoiceTestDataBuilder invoiceTestDataBuilder;

    public InvoiceRestControllerTests() {
        invoiceTestDataBuilder = new InvoiceTestDataBuilder();
    }

    @Before
    public void setUp() {
        invoice = invoiceTestDataBuilder.build();
        sut = new InvoiceRestController(invoiceService);
    }

    @After
    public void tearDown() {
        sut = null;
        invoice = null;
    }

    @Test
    public void listAllInvoicesTest() throws BaseException {
        VehicleType vehicleType = vehicleTypeService.getCurrentVehicleType(invoice.getVehicle().getVehicleType().getName()).get();
        invoice.getVehicle().setVehicleType(vehicleType);
        invoice.getParkingRates().setVehicleType(vehicleType);
        invoiceService.save(invoice);
        Page<InvoiceDTO> invoiceDTOPage = sut.listAllInvoices(PageRequest.of(1, 2));
        assertNotNull("Invoice page retrieved is a null object", invoiceDTOPage);
        assertEquals("Invoice page size is different to expected", 1, invoiceDTOPage.getTotalElements());
    }

    @Test
    public void listVehiclesInParkingTest() throws BaseException {
        invoice.getVehicle().setVehicleType(vehicleTypeService.getCurrentVehicleType(invoice.getVehicle().getVehicleType().getName()).get());
        parkingAttendant.vehicleCheckIn(invoice.getVehicle());
        Page<InvoiceDTO> invoiceDTOPage = sut.listVehiclesInParking(PageRequest.of(1, 2));
        assertNotNull("Invoice page retrieved is a null object", invoiceDTOPage);
        assertEquals("Invoice page size is different to expected", 1, invoiceDTOPage.getTotalElements());
    }
}
