package com.backend.exceptions;

import java.util.Arrays;

import org.springframework.http.HttpStatus;

public class RequiredFieldsException extends ApiException{

    private static final long serialVersionUID = 1L;
    
    public RequiredFieldsException(Object [] fields){
        super("Erro: os campos " + Arrays.toString(fields) + " são requiridos mas não foram fornecidos!", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ApiExceptionObject getApiExceptionObject(){
        return new ApiExceptionObject(this.getStatus().value(), "Dados insuficientes", this.getMessage());
    }
}
