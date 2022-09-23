package com.alwaysbedream.survbackend.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alwaysbedream.survbackend.entity.Error.ErrorResponses;
import com.alwaysbedream.survbackend.exceptions.EtAuthException;

@ControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(EtAuthException.class)
    public ResponseEntity<Object> handleAuthException(EtAuthException e){
        ErrorResponses error = new ErrorResponses(false, e.getMessage() , HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(error , HttpStatus.UNAUTHORIZED);
    }
}
