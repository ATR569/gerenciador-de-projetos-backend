package com.backend.exceptions;

import com.backend.model.UserTypeEnum;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException {

    private static final long serialVersionUID = 1L;
    
    public UserNotFoundException(UserTypeEnum tipoUsuario){
        super("Erro: ".concat(tipoUsuario.getDescricao()).concat(" não encontrado!"), HttpStatus.NOT_FOUND);
    }

    @Override
    public ApiExceptionObject  getApiExceptionObject(){
        return new ApiExceptionObject(this.getStatus().value(), this.getMessage(), "Erro");
    }
}
