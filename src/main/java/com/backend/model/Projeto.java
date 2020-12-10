// package com.backend.model;

// import java.io.Serializable;

// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;
// import lombok.NoArgsConstructor;
// import lombok.NonNull;

// import java.util.List;

// import javax.persistence.Entity;
// import javax.persistence.Id;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.JoinColumn;
// import javax.persistence.ManyToOne;

// @Data
// @AllArgsConstructor
// @NoArgsConstructor
// @Builder
// @Entity
// public class Projeto implements Serializable{

//     private static final long serialVersionUID = 1L;

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Integer id;

//     @NonNull
//     private String nome;

//     @NonNull
//     private String descricao;
    
//     @ManyToOne
//     @JoinColumn(name="professor_id")
//     private Professor coordenador;

//     List<Colaborador> colaboradores;
// }
