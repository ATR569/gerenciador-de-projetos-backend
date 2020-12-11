package com.backend.controller;

import java.util.HashSet;
import java.util.Optional;

import com.backend.dto.ProjetoDTO;
import com.backend.model.Professor;
import com.backend.model.Projeto;
import com.backend.repository.ProfessorRepository;
import com.backend.repository.ProjetoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Professor obj) {
        Professor professor = Professor.builder().nome(obj.getNome()).matricula(obj.getMatricula())
                .senha(obj.getSenha()).areaAtuacao(obj.getAreaAtuacao()).formacao(obj.getFormacao()).build();

        try {
            professorRepository.save(professor);
            return new ResponseEntity<>(professor, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("{id}/projetos")
    public ResponseEntity<?> createProjeto(@PathVariable("id") int id, @RequestBody ProjetoDTO obj) {
        Optional<Professor> coordenador = professorRepository.findById(id);

        if (coordenador.isEmpty())
            return new ResponseEntity<>("Professor não encontrado!", HttpStatus.NOT_FOUND);

        Projeto projeto = Projeto.builder().nome(obj.getNome()).descricao(obj.getDescricao())
                .coordenador(coordenador.get()).colaboradores(new HashSet<>()).build();

        try {
            projetoRepository.save(projeto);
            return new ResponseEntity<>(projeto, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getProfessores() {
        return ResponseEntity.ok(professorRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getProfessorById(@PathVariable("id") int id) {
        Optional<Professor> professor = professorRepository.findById(id);

        if (professor.isEmpty())
            return new ResponseEntity<>("Professor não encontrado!", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(professorRepository.findById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editProfessor(@PathVariable("id") int id, @RequestBody Professor professor) {
        Optional<Professor> p = professorRepository.findById(id);

        if (p.isEmpty())
            return new ResponseEntity<>("Professor não encontrado!", HttpStatus.NOT_FOUND);

        try {
            professor.setId(id);
            professor.setSenha((p.get().getSenha()));
            professorRepository.save(professor);
            return ResponseEntity.ok(professor);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro Interno! Tente Novamente!", HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProfessor(@PathVariable int id) {
        Optional<Professor> p = professorRepository.findById(id);

        if (p.isEmpty())
            return new ResponseEntity<>("Professor não encontrado!", HttpStatus.NOT_FOUND);

        try {
            professorRepository.deleteById(p.get().getId());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return new ResponseEntity<>("Erro Interno! Tente Novamente!", HttpStatus.BAD_GATEWAY);
        }
    }
}
