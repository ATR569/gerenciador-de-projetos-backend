package com.backend.exceptions;

import org.springframework.http.HttpStatus;

public class EmptyBodyException extends ApiException {
    private static final long serialVersionUID = 1L;

    public EmptyBodyException(){
        super("Dados não foram fornecidos!", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ApiExceptionObject getApiExceptionObject() {
        return new ApiExceptionObject(this.getStatus().value(), this.getMessage(), "Dados insuficientes");
    }
    
}
