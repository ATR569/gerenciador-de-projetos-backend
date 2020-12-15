package com.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.backend.dto.ColaboradorDTO;
import com.backend.dto.ColaboradorResponseDTO;
import com.backend.dto.ProjetoDTO;
import com.backend.exceptions.ApiException;
import com.backend.exceptions.EmptyBodyException;
import com.backend.exceptions.ObjectNotFoundException;
import com.backend.exceptions.RequiredFieldsException;
import com.backend.model.Aluno;
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
    private AlunoController alunoController;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @PostMapping("{id}/colaboradores")
    public ResponseEntity<?> addColaborador(@PathVariable("id") int id, @RequestBody ColaboradorDTO colaboradorDTO) {
        try{
            Projeto projeto = findProjetoById(id);
            validarDTO(colaboradorDTO);
            
            Aluno aluno = alunoController.findAlunoById(colaboradorDTO.getIdAluno());

            Colaborador colaborador = Colaborador.builder()
                                .aluno(aluno)
                                .papel(colaboradorDTO.getPapel())
                                .build();

            colaboradorRepository.save(colaborador);
            projeto.getColaboradores().add(colaborador);

            projetoRepository.save(projeto);

            ColaboradorResponseDTO colaboradorResponseDTO = ColaboradorResponseDTO.builder()
                                .id(colaborador.getId())
                                .aluno(aluno)
                                .projeto(new ProjetoDTO(projeto))
                                .papel(colaborador.getPapel())
                                .build();

            return ResponseEntity.ok(colaboradorResponseDTO);
        } catch (ApiException e) {
            return new ResponseEntity<>(e.getApiExceptionObject(), e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>("ERRO INTERNO NO SERVIDOR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getProjetos() {
        try {
            List<ProjetoDTO> projetosDTO = new ArrayList<>();

            projetoRepository.findAll().forEach(projeto -> projetosDTO.add(new ProjetoDTO(projeto)));

            return ResponseEntity.ok(projetosDTO);
        } catch (Exception e) {
            return new ResponseEntity<>("ERRO INTERNO NO SERVIDOR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getProjetoById(@PathVariable("id") int id) {
        try {
            Projeto projeto = findProjetoById(id);

            return ResponseEntity.ok(projeto);
        } catch (ApiException e) {
            return new ResponseEntity<>(e.getApiExceptionObject(), e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>("ERRO INTERNO NO SERVIDOR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    protected void validarColaborador(Colaborador colaborador) throws ApiException {
        if (colaborador == null)
            throw new EmptyBodyException();
        
        List<String> emptyFields = new ArrayList<>();

        if (colaborador.getAluno() == null)
            emptyFields.add("aluno_id");
        if (colaborador.getPapel() == null)
            emptyFields.add("papel");

        if (emptyFields.size() > 0)
            throw new RequiredFieldsException(emptyFields.toArray());
    }

    protected void validarDTO(ColaboradorDTO dto) throws ApiException {
        if (dto == null)
            throw new EmptyBodyException();
        
        List<String> emptyFields = new ArrayList<>();

        if (dto.getIdAluno() == null)
            emptyFields.add("aluno_id");
        if (dto.getPapel() == null)
            emptyFields.add("papel");

        if (emptyFields.size() > 0)
            throw new RequiredFieldsException(emptyFields.toArray());
    }

    protected Projeto findProjetoById(int id) throws ApiException {
        Optional<Projeto> projeto = projetoRepository.findById(id);

        if (projeto.isEmpty())
            throw new ObjectNotFoundException("Projeto");

        return projeto.get();
    }
}
