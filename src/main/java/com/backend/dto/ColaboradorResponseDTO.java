package com.backend.dto;

import java.io.Serializable;

import com.backend.model.Aluno;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ColaboradorResponseDTO implements Serializable{
 
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Aluno aluno;
    private String papel;
    private ProjetoDTO projeto;
}
