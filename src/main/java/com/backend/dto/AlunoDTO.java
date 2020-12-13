package com.backend.dto;

import com.backend.model.Aluno;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class AlunoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    public AlunoDTO(Aluno aluno){
        this.id = aluno.getId();
        this.nome = aluno.getNome();
        this.senha = aluno.getSenha();
        this.curso = aluno.getCurso();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NonNull
    private String nome;
    
    @JsonProperty( value = "senha", access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    @NonNull
    private String curso;
}