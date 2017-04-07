package com.dominik.backend.response;

import org.springframework.http.HttpStatus;

/**
 * Created by dominik on 07.04.17.
 */
public class AppResponse {

    private HttpStatus status;
    private String message;

    public AppResponse() {}

    public AppResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
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
}
