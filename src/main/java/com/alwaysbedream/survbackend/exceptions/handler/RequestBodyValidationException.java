package com.alwaysbedream.survbackend.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alwaysbedream.survbackend.entity.Error.ErrorResponses;

@ControllerAdvice
public class RequestBodyValidationException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> RequestBodyValidationException(MethodArgumentNotValidException e){
        FieldError er = e.getFieldError();
        ErrorResponses error = new ErrorResponses(false, er.getDefaultMessage() , HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error , HttpStatus.BAD_REQUEST);
    }
}