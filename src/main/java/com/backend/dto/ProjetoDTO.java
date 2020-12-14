package com.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.backend.model.Professor;
import com.backend.model.Projeto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjetoDTO {
    private Integer id;
    private String nome;
    private String descricao;
    private Professor coordenador;

    public ProjetoDTO(Projeto prj){
        this.id = prj.getId();
        this.nome = prj.getNome();
        this.descricao = prj.getDescricao();
        this.coordenador = prj.getCoordenador();
    }
}