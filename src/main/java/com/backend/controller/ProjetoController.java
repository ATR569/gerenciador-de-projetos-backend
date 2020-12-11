package com.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.backend.dto.ProjetoDTO;
import com.backend.model.Colaborador;
import com.backend.model.Projeto;
import com.backend.repository.ColaboradorRepository;
import com.backend.repository.ProjetoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/projetos")
public class ProjetoController {
    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @PostMapping("{id}/colaboradores")
    public ResponseEntity<?> createProjeto(@PathVariable("id") int id, @RequestBody Colaborador obj) {
        Optional<Projeto> projeto = projetoRepository.findById(id);

        if (projeto.isEmpty())
            return new ResponseEntity<>("Projeto não encontrado!", HttpStatus.NOT_FOUND);

        Colaborador colaborador = Colaborador.builder().projeto(projeto.get()).aluno(obj.getAluno())
                .papel(obj.getPapel()).build();

        try {
            colaboradorRepository.save(colaborador);
            return new ResponseEntity<>(colaborador, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getProjetos() {
        List<ProjetoDTO> projetosDTO = new ArrayList<>();

        projetoRepository.findAll().forEach(projeto -> projetosDTO.add(new ProjetoDTO(projeto)));

        return ResponseEntity.ok(projetosDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getProjetoById(@PathVariable("id") int id) {
        Optional<Projeto> projeto = projetoRepository.findById(id);

        if (projeto.isEmpty())
            return new ResponseEntity<>("Projeto não encontrado!", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(projetoRepository.findById(id));
    }

}
