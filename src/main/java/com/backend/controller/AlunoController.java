package com.backend.controller;

import java.util.Optional;

import com.backend.model.Aluno;
import com.backend.repository.AlunoRepository;

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
@RequestMapping(value = "/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Aluno obj) {
        Aluno aluno = Aluno.builder().nome(obj.getNome()).matricula(obj.getMatricula()).senha(obj.getSenha())
                .curso(obj.getCurso()).build();

        try {
            alunoRepository.save(aluno);
            return new ResponseEntity<>(aluno, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAlunos() {
        return ResponseEntity.ok(alunoRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getAlunoById(@PathVariable("id") int id) {
        Optional<Aluno> a = alunoRepository.findById(id);

        if (a.isEmpty())
            return new ResponseEntity<>("Aluno não encontrado!", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(alunoRepository.findById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editAluno(@PathVariable("id") int id, @RequestBody Aluno aluno) {
        Optional<Aluno> a = alunoRepository.findById(id);

        if (a.isEmpty())
            return new ResponseEntity<>("Aluno não encontrado!", HttpStatus.NOT_FOUND);

        try {
            aluno.setId(id);
            aluno.setSenha((a.get().getSenha()));
            alunoRepository.save(aluno);
            return ResponseEntity.ok(aluno);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro Interno! Tente Novamente!", HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAluno(@PathVariable int id) {
        Optional<Aluno> a = alunoRepository.findById(id);

        if (a.isEmpty())
            return new ResponseEntity<>("Aluno não encontrado!", HttpStatus.NOT_FOUND);

        try {
            alunoRepository.deleteById(a.get().getId());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return new ResponseEntity<>("Erro Interno! Tente Novamente!", HttpStatus.BAD_GATEWAY);
        }
    }
}