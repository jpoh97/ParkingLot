package co.com.ceiba.parkinglotbackend.exceptions.implementations;

import co.com.ceiba.parkinglotbackend.exceptions.BaseException;

public class InvoiceDataException extends BaseException {

    public InvoiceDataException() {
        super("Invoice data is wrong");
    }
}
