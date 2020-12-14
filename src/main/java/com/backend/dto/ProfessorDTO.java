package com.backend.dto;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.backend.model.Professor;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfessorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    public ProfessorDTO(Professor professor){
        this.id = professor.getId();
        this.nome = professor.getNome();
        this.senha = professor.getSenha();
        this.formacao = professor.getFormacao();
        this.areaAtuacao = professor.getAreaAtuacao();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NonNull
    private String nome;
    
    @JsonProperty( value = "senha", access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    @NonNull
    private String formacao;    

    @NonNull
    private String areaAtuacao;    
}
