package com.backend.exceptions;

import org.springframework.http.HttpStatus;

public class ObjectNotFoundException extends ApiException {

    private static final long serialVersionUID = 1L;
    
    public ObjectNotFoundException(String descricao){
        super("Erro: ".concat(descricao).concat(" não encontrado!"), HttpStatus.NOT_FOUND);
    }

    @Override
    public ApiExceptionObject  getApiExceptionObject(){
        return new ApiExceptionObject(this.getStatus().value(), "Erro", this.getMessage());
    }
}
