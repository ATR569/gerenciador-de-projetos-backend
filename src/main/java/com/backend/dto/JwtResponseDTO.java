package com.backend.dto;

import java.io.Serializable;

import com.backend.model.UsuarioIF;

public class JwtResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String jwttoken;
    private final UsuarioIF usuario;

    public JwtResponseDTO(String jwttoken, UsuarioIF usuario) {
        this.jwttoken = jwttoken;
        this.usuario = usuario;
    }

    public String getJwtToken() {
        return jwttoken;
    }

    public UsuarioIF getUsuario(){
        return this.usuario;
    }
}
