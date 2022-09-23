package com.alwaysbedream.survbackend.exceptions;

public class EtMethodArgumentNotValidException extends RuntimeException {
    public EtMethodArgumentNotValidException(String message){
        super(message);
    }
    public EtMethodArgumentNotValidException(String message , Throwable cause){
        super(message , cause);
    }
}
