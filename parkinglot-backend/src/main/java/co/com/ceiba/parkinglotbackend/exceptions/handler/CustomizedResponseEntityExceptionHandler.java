package co.com.ceiba.parkinglotbackend.exceptions.handler;

import co.com.ceiba.parkinglotbackend.exceptions.*;
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
    public ErrorReponse notFoundRequest(Exception exception, HttpServletRequest request) {
        return new ErrorReponse(exception.getMessage(), request.getRequestURI(), request.getMethod(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            InvalidDatesException.class,
            VehicleDataException.class
    })
    @ResponseBody
    public ErrorReponse badRequest(Exception exception, HttpServletRequest request) {
        return new ErrorReponse(exception.getMessage(), request.getRequestURI(), request.getMethod(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({
            InvalidDayLicensePlateException.class
    })
    @ResponseBody
    public ErrorReponse forbiddenRequest(Exception exception, HttpServletRequest request) {
        return new ErrorReponse(exception.getMessage(), request.getRequestURI(), request.getMethod(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler({
            NoSpaceAvailableException.class
    })
    @ResponseBody
    public ErrorReponse noContentRequest(Exception exception, HttpServletRequest request) {
        return new ErrorReponse(exception.getMessage(), request.getRequestURI(), request.getMethod(), LocalDateTime.now());
    }
}