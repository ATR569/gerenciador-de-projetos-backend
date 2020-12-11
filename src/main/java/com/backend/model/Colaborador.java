package com.backend.model;

import java.io.Serializable;

// import javax.persistence.JoinColumn;
// import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Colaborador implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name="projeto_id")
    private Projeto projeto;

    private static final long serialVersionUID = 1L;

    @NonNull
    private String papel;
}
