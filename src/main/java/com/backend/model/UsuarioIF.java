package com.backend.model;

public interface UsuarioIF {
    public Integer getId();
    public String getMatricula();
    public String getSenha();
    public UserTypeEnum getTipoUsuario();
}
