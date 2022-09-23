package com.alwaysbedream.survbackend.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alwaysbedream.survbackend.entity.Error.ErrorResponses;
import com.alwaysbedream.survbackend.exceptions.EtResourceNotFoundException;

@ControllerAdvice
public class ResourceNotFoundHandler {
    @ExceptionHandler(EtResourceNotFoundException.class)
    public ResponseEntity<Object> handleAuthException(EtResourceNotFoundException e){
        ErrorResponses error = new ErrorResponses(false, e.getMessage() , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error , HttpStatus.NOT_FOUND);
    }
}
