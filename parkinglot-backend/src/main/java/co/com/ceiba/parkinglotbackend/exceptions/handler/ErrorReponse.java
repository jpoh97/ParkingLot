package co.com.ceiba.parkinglotbackend.exceptions.handler;

import java.time.LocalDateTime;

public class ErrorReponse {
    private String exception;
    private String request;
    private String method;
    private LocalDateTime localDateTime;

    public ErrorReponse(String exception, String request, String method, LocalDateTime localDateTime) {
        this.exception = exception;
        this.request = request;
        this.method = method;
        this.localDateTime = localDateTime;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public LocalDateTime getLocalDate() {
        return localDateTime;
    }

    public void setLocalDate(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
