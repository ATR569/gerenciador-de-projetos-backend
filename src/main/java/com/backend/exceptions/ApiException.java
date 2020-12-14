package com.backend.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class ApiException extends Exception {
    private static final long serialVersionUID = 1L;
    private HttpStatus status;
    
    public abstract ApiExceptionObject getApiExceptionObject();
    
    public ApiException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
}
