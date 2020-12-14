package com.backend.repository;

import com.backend.model.Colaborador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Integer>{
    List<Colaborador> findByIdProjeto(int idProjeto);
}