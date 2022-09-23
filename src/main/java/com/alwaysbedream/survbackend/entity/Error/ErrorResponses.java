package com.alwaysbedream.survbackend.entity.Error;

import org.springframework.http.HttpStatus;

public class ErrorResponses {
    private Boolean success;
    private String message;
    private HttpStatus httpStatus;

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorResponses(Boolean success, String message, HttpStatus httpStatus) {
        this.success = success;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
