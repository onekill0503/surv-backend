package com.alwaysbedream.survbackend.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alwaysbedream.survbackend.entity.Error.ErrorResponses;
import com.alwaysbedream.survbackend.exceptions.EtBadRequestException;

@ControllerAdvice
public class BadRequestHandler {
    @ExceptionHandler(EtBadRequestException.class)
    public ResponseEntity<Object> handleAuthException(EtBadRequestException e){
        ErrorResponses error = new ErrorResponses(false, e.getMessage() , HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error , HttpStatus.BAD_REQUEST);
    }
}
