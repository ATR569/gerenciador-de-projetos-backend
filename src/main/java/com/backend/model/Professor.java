package com.backend.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Professor implements Serializable, UsuarioIF{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NonNull
    private String matricula;
    
    @NonNull
    private String nome;
    
    @JsonProperty( value = "senha", access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    @NonNull
    private String areaAtuacao;

    @NonNull
    private String formacao;

    @Override
    @JsonIgnore
    public UserTypeEnum getTipoUsuario(){
        return UserTypeEnum.PROFESSOR;
    }
}
