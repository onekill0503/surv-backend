package com.alwaysbedream.survbackend.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alwaysbedream.survbackend.entity.Error.ErrorResponses;

@ControllerAdvice
public class MismatchExceptionHandler {
    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<Object> handleMismatchException(ClassCastException e){
        ErrorResponses error = new ErrorResponses(false, "Invalid Datatype" , HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error , HttpStatus.BAD_REQUEST);
    }
}
