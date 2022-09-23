package com.alwaysbedream.survbackend.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alwaysbedream.survbackend.entity.Error.ErrorResponses;

@ControllerAdvice
public class NoRequestBodyHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> NoRequestBodyHandler(HttpMessageNotReadableException e){
        ErrorResponses error = new ErrorResponses(false, "Request Body is Required !" , HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error , HttpStatus.BAD_REQUEST);
    }
}
