package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.model.Projeto;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Integer>{
}
