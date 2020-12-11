package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer>{
}
