package co.com.ceiba.parkinglotbackend.unittest.core.services;

import co.com.ceiba.parkinglotbackend.core.entities.Invoice;
import co.com.ceiba.parkinglotbackend.core.repositories.InvoiceRepository;
import co.com.ceiba.parkinglotbackend.core.services.InvoiceService;
import co.com.ceiba.parkinglotbackend.core.services.implementations.InvoiceServiceImplementation;
import co.com.ceiba.parkinglotbackend.exceptions.implementations.InvoiceDataException;
import co.com.ceiba.parkinglotbackend.testdatabuilder.entities.InvoiceTestDataBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class InvoiceServiceTests {

    @Mock
    private InvoiceRepository mockInvoiceRepository;

    private InvoiceService sut;
    private Invoice invoice;
    private InvoiceTestDataBuilder invoiceTestDataBuilder;

    @Before
    public void setUp() {
        invoiceTestDataBuilder = new InvoiceTestDataBuilder();
        invoice = invoiceTestDataBuilder.build();
    }

    @After
    public void tearDown() {
        invoice = null;
        invoiceTestDataBuilder = null;
    }

    @Test
    public void saveCorrectInvoice() throws InvoiceDataException {
        when(mockInvoiceRepository.save(any())).thenReturn(invoice);

        sut = new InvoiceServiceImplementation(mockInvoiceRepository);
        invoice = sut.save(invoice);

        assertNotNull("Cannot save the invoice", invoice);
    }

    @Test(expected = InvoiceDataException.class)
    public void saveWithoutInvoice() throws InvoiceDataException {
        sut = new InvoiceServiceImplementation(mockInvoiceRepository);
        invoice = sut.save(null);
    }

    @Test
    public void getAllTest() throws InvoiceDataException {
        // arrange
        List<Invoice> invoices = getListOfInvoices();
        Page<Invoice> invoicePageParam = new PageImpl<>(invoices);

        // act
        when(mockInvoiceRepository.findAll(PageRequest.of(1, 2))).thenReturn(invoicePageParam);
        sut = new InvoiceServiceImplementation(mockInvoiceRepository);
        Page<Invoice> invoicePage = sut.getAll(PageRequest.of(1, 2));

        // assert
        assertNotNull("Invoice list is null", invoicePage);
        assertEquals("Invoice list is empty", invoicePage.getTotalElements(), invoices.size());
    }

    @Test(expected = InvoiceDataException.class)
    public void getAllWithoutPageableTest() throws InvoiceDataException {
        sut = new InvoiceServiceImplementation(mockInvoiceRepository);
        sut.getAll(null);
    }

    @Test
    public void getAllInParkingTest() throws InvoiceDataException {
        // arrange
        List<Invoice> invoices = getListOfInvoices();
        Page<Invoice> invoicePageParam = new PageImpl<>(invoices);

        // act
        when(mockInvoiceRepository.findAllByDepartureDateIsNull(PageRequest.of(1, 2))).thenReturn(invoicePageParam);
        sut = new InvoiceServiceImplementation(mockInvoiceRepository);
        Page<Invoice> invoicePage = sut.getAllInParking(PageRequest.of(1, 2));

        // assert
        assertNotNull("Invoice list is null", invoicePage);
        assertEquals("Invoice list is empty", invoicePage.getTotalElements(), invoices.size());
    }

    @Test(expected = InvoiceDataException.class)
    public void getAllInParkingWithoutPageableTest() throws InvoiceDataException {
        sut = new InvoiceServiceImplementation(mockInvoiceRepository);
        sut.getAllInParking(null);
    }

    @Test
    public void getParkingSpacesInUseForVehicleTypeTest() throws InvoiceDataException {
        // arrange
        List<Invoice> invoices = getListOfInvoices();
        when(mockInvoiceRepository.findAllByVehicleVehicleTypeNameAndDepartureDateIsNull(any())).thenReturn(invoices.stream());

        // act
        sut = new InvoiceServiceImplementation(mockInvoiceRepository);
        Stream<Invoice> invoiceStream = sut.getParkingSpacesInUseForVehicleType(invoice.getVehicle().getVehicleType().getName());

        // assert
        assertNotNull("Invoice stream is null", invoiceStream);
        assertEquals("Invoice stream is empty", invoiceStream.count(), invoices.size());
    }

    @Test(expected = InvoiceDataException.class)
    public void getParkingSpacesInUseForVehicleTypeWithoutVehicleTypeTest() throws InvoiceDataException {
        sut = new InvoiceServiceImplementation(mockInvoiceRepository);
        sut.getParkingSpacesInUseForVehicleType(null);
    }

    @Test
    public void getParkingSpacesCountInUseForVehicleTypeTest() throws InvoiceDataException {
        Long countMock = 2L;
        when(mockInvoiceRepository.countByVehicleVehicleTypeNameAndDepartureDateIsNull(any())).thenReturn(countMock);
        sut = new InvoiceServiceImplementation(mockInvoiceRepository);

        Long countReturn = sut.getParkingSpacesCountInUseForVehicleType(invoice.getVehicle().getVehicleType().getName());
        assertEquals("Count not equals", countMock, countReturn);
    }

    @Test(expected = InvoiceDataException.class)
    public void getParkingSpacesCountInUseForVehicleTypeWithoutVehicleTypeNameTest() throws InvoiceDataException {
        sut = new InvoiceServiceImplementation(mockInvoiceRepository);
        sut.getParkingSpacesCountInUseForVehicleType(null);
    }

    @Test
    public void getVehicleInParkingTest() throws InvoiceDataException {
        // arrange
        when(mockInvoiceRepository.findByVehicleLicensePlateAndDepartureDateIsNull(any())).thenReturn(Optional.ofNullable(invoice));
        sut = new InvoiceServiceImplementation(mockInvoiceRepository);

        // act
        Optional<Invoice> invoiceReturn = sut.getVehicleInParking(invoice.getVehicle().getLicensePlate());

        // assert
        assertTrue("Invoice return is null", invoiceReturn.isPresent());
        assertEquals("License plates are not equals", invoice.getVehicle().getLicensePlate(),
                invoiceReturn.get().getVehicle().getLicensePlate());
        assertEquals("Entry dates are not equals", invoice.getEntryDate(), invoiceReturn.get().getEntryDate());
        assertEquals("Exit dates are not equals", invoice.getDepartureDate(), invoiceReturn.get().getDepartureDate());
    }

    @Test(expected = InvoiceDataException.class)
    public void getVehicleInParkingWithoutLicensePlateTest() throws InvoiceDataException {
        sut = new InvoiceServiceImplementation(mockInvoiceRepository);
        sut.getVehicleInParking(null);
    }

    private List<Invoice> getListOfInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice);
        invoice = invoiceTestDataBuilder.withEntryDate(LocalDateTime.now().minusYears(1)).build();
        invoices.add(invoice);
        return invoices;
    }
}
