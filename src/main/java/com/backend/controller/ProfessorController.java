package com.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.backend.dto.ProfessorDTO;
import com.backend.dto.ProjetoDTO;
import com.backend.exceptions.ApiException;
import com.backend.exceptions.EmptyBodyException;
import com.backend.exceptions.RequiredFieldsException;
import com.backend.exceptions.UserNotFoundException;
import com.backend.model.Professor;
import com.backend.model.Projeto;
import com.backend.model.UserTypeEnum;
import com.backend.model.Usuario;
import com.backend.repository.ProfessorRepository;
import com.backend.repository.ProjetoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins="*")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody ProfessorDTO professorDTO) {
        try {
            validarDTO(professorDTO);

            Professor professor = Professor.builder()
                                .nome(professorDTO.getNome())
                                .matricula(Usuario.gerarMatricula(UserTypeEnum.PROFESSOR))
                                .senha(professorDTO.getSenha())
                                .areaAtuacao(professorDTO.getAreaAtuacao())
                                .formacao(professorDTO.getFormacao())
                                .build();

            professorRepository.save(professor);
            return new ResponseEntity<>(professor, HttpStatus.CREATED);
        } catch (ApiException e) {
            return new ResponseEntity<>(e.getApiExceptionObject(), e.getStatus());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("{id}/projetos")
    public ResponseEntity<?> createProjeto(@PathVariable("id") int id, @RequestBody ProjetoDTO projetoDTO) {
        try {
            Professor coordenador = findProfessorById(id);
            validarDTO(projetoDTO);

            Projeto projeto = Projeto.builder()
                                .coordenador(coordenador)
                                .nome(projetoDTO.getNome())
                                .descricao(projetoDTO.getDescricao())
                                .colaboradores(new ArrayList<>())
                                .build();

            projetoRepository.save(projeto);
            return new ResponseEntity<>(projeto, HttpStatus.CREATED);
        } catch (ApiException e) {
            return new ResponseEntity<>(e.getApiExceptionObject(), e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>("Erro Interno! Tente Novamente!", HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping
    public ResponseEntity<?> getProfessores() {
        return ResponseEntity.ok(professorRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getProfessorById(@PathVariable("id") int id) {
        try {
            Professor professor = findProfessorById(id);

            return ResponseEntity.ok(professor);
        } catch (ApiException e) {
            return new ResponseEntity<>(e.getApiExceptionObject(), e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>("Erro Interno! Tente Novamente!", HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editProfessor(@PathVariable("id") int id, @RequestBody ProfessorDTO professorDTO) {
        try {
            Professor professor = findProfessorById(id);
            
            validarDTO(professorDTO);

            professor.setNome(professorDTO.getNome());
            professor.setSenha(professorDTO.getSenha());
            professor.setAreaAtuacao(professorDTO.getAreaAtuacao());
            professor.setFormacao(professorDTO.getFormacao());
            
            professorRepository.save(professor);
            return ResponseEntity.ok(professor);
        } catch (ApiException e) {
            return new ResponseEntity<>(e.getApiExceptionObject(), e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>("Erro Interno! Tente Novamente!", HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProfessor(@PathVariable int id) {
        try {
            Professor professor = findProfessorById(id);
            professorRepository.deleteById(professor.getId());

            return ResponseEntity.noContent().build();
        } catch (ApiException e) {
            return new ResponseEntity<>(e.getApiExceptionObject(), e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>("Erro Interno! Tente Novamente!", HttpStatus.BAD_GATEWAY);
        }
    }

    protected void validarDTO(ProfessorDTO dto) throws ApiException {
        if (dto == null)
            throw new EmptyBodyException();
        
        List<String> emptyFields = new ArrayList<>();

        if (dto.getNome() == null)
            emptyFields.add("nome");
        if (dto.getSenha() == null)
            emptyFields.add("senha");
        if (dto.getFormacao() == null)
            emptyFields.add("formacao");
        if (dto.getAreaAtuacao() == null)
            emptyFields.add("area de atuacao");

        if (emptyFields.size() > 0)
            throw new RequiredFieldsException(emptyFields.toArray());
    }

    protected void validarDTO(ProjetoDTO dto) throws ApiException {
        if (dto == null)
            throw new EmptyBodyException();
        
        List<String> emptyFields = new ArrayList<>();

        if (dto.getNome() == null)
            emptyFields.add("nome");
        if (dto.getDescricao() == null)
            emptyFields.add("descricao");

        if (emptyFields.size() > 0)
            throw new RequiredFieldsException(emptyFields.toArray());
    }

    protected Professor findProfessorById(int id) throws ApiException{
        Optional<Professor> a = professorRepository.findById(id);

        if (a == null)
            throw new UserNotFoundException(UserTypeEnum.PROFESSOR);

        return a.get();
    }

}
