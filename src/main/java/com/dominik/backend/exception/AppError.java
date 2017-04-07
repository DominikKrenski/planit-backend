package com.dominik.backend.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Created by dominik on 07.04.17.
 */
public class AppError {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public AppError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public AppError(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
