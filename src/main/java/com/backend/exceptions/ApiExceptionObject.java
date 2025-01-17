package com.backend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiExceptionObject {
    private Integer status;
    private String descricao;
    private String mensagem;

    @Override
    public String toString(){
        return "{ \"status\": \"" + this.status + "\", \"descricao\": \"" + this.descricao + "\", \"mensagem\": \"" + this.mensagem + "\" }";
    }
}
