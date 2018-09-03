package co.com.ceiba.parkinglotbackend.exceptions.handler;

import co.com.ceiba.parkinglotbackend.exceptions.implementations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            VehicleDoesNotExistException.class
    })
    @ResponseBody
    public ErrorResponse notFoundRequest(Exception exception, HttpServletRequest request) {
        return new ErrorResponse(exception.getMessage(), request.getRequestURI(), request.getMethod(), LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            InvalidDatesException.class,
            VehicleDataException.class,
            VehicleAlreadyExistsInParkingLotException.class,
            InvoiceDataException.class,
            VehicleTypeDataException.class,
            ParkingRatesDataException.class,
            NoSpaceAvailableException.class
    })
    @ResponseBody
    public ErrorResponse badRequest(Exception exception, HttpServletRequest request) {
        return new ErrorResponse(exception.getMessage(), request.getRequestURI(), request.getMethod(), LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({
            InvalidDayLicensePlateException.class
    })
    @ResponseBody
    public ErrorResponse forbiddenRequest(Exception exception, HttpServletRequest request) {
        return new ErrorResponse(exception.getMessage(), request.getRequestURI(), request.getMethod(), LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
    }
}