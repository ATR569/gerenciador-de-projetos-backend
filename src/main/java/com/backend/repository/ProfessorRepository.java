package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.backend.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer>{
    Optional<Professor> findByMatricula(String matricula);
}
