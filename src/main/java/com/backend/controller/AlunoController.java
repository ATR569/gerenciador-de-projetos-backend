package com.backend.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.backend.dto.AlunoDTO;
import com.backend.exceptions.ApiException;
import com.backend.exceptions.EmptyBodyException;
import com.backend.exceptions.RequiredFieldsException;
import com.backend.exceptions.UserNotFoundException;
import com.backend.model.Aluno;
import com.backend.model.UserTypeEnum;
import com.backend.model.Usuario;
import com.backend.repository.AlunoRepository;

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
@RequestMapping(value = "/api/alunos")
@CrossOrigin(origins="*")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody AlunoDTO obj) {
        try {
            validarDTO(obj);

            Aluno aluno = Aluno.builder().nome(obj.getNome())
                                .matricula(Usuario.gerarMatricula(UserTypeEnum.ALUNO))
                                .senha(obj.getSenha())
                                .curso(obj.getCurso())
                                .build();

            alunoRepository.save(aluno);
            return new ResponseEntity<>(aluno, HttpStatus.CREATED);
        } catch (ApiException e) {
            return new ResponseEntity<>(e.getApiExceptionObject(), e.getStatus());
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
        try{
            Aluno aluno = findAlunoById(id); 
            return ResponseEntity.ok(aluno);
        } catch (ApiException e) {
            return new ResponseEntity<>(e.getApiExceptionObject(), e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>("Erro Interno! Tente Novamente!", HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editAluno(@PathVariable("id") int id, @RequestBody AlunoDTO alunoDTO) {
        try {
            Aluno aluno = findAlunoById(id);
            
            validarDTO(alunoDTO);

            aluno.setCurso(alunoDTO.getCurso());
            aluno.setNome(alunoDTO.getNome());
            aluno.setSenha(alunoDTO.getSenha());
            
            alunoRepository.save(aluno);
            return ResponseEntity.ok(aluno);
        } catch (ApiException e) {
            return new ResponseEntity<>(e.getApiExceptionObject(), e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>("Erro Interno! Tente Novamente!", HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAluno(@PathVariable int id) {
        try {
            Aluno aluno = findAlunoById(id);
            alunoRepository.deleteById(aluno.getId());

            return ResponseEntity.noContent().build();
        } catch (ApiException e) {
            return new ResponseEntity<>(e.getApiExceptionObject(), e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>("Erro Interno! Tente Novamente!", HttpStatus.BAD_GATEWAY);
        }
    }

    protected void validarDTO(AlunoDTO dto) throws ApiException {
        if (dto == null)
            throw new EmptyBodyException();
        
        List<String> emptyFields = new ArrayList<>();

        if (dto.getNome() == null)
            emptyFields.add("nome");
        if (dto.getSenha() == null)
            emptyFields.add("senha");
        if (dto.getCurso() == null)
            emptyFields.add("curso");

        if (emptyFields.size() > 0)
            throw new RequiredFieldsException(emptyFields.toArray());
    }

    protected Aluno findAlunoById(int id) throws ApiException{
        Optional<Aluno> a = alunoRepository.findById(id);

        if (a.isEmpty())
            throw new UserNotFoundException(UserTypeEnum.ALUNO);

        return a.get();
    }
}