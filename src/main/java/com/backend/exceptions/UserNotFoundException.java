package com.backend.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException {

    private static final long serialVersionUID = 1L;
    
    public UserNotFoundException(String tipoUsuario){
        super("Erro: " + tipoUsuario + " não encontrado!", HttpStatus.NOT_FOUND);
    }

    @Override
    public ApiExceptionObject getApiExceptionObject(){
        return new ApiExceptionObject(this.getStatus().value(), "Erro", this.getMessage());
    }
}
