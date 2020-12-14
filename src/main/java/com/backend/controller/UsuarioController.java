package com.backend.controller;

import java.util.Optional;

import com.backend.exceptions.ApiException;
import com.backend.exceptions.UserNotFoundException;
import com.backend.model.Aluno;
import com.backend.model.Professor;
import com.backend.model.UserTypeEnum;
import com.backend.model.Usuario;
import com.backend.model.UsuarioIF;
import com.backend.repository.AlunoRepository;
import com.backend.repository.ProfessorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login")
public class UsuarioController {
    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    private Professor getAsProfessor(Usuario obj) throws ApiException{
        Optional<Professor> p = professorRepository.findByMatricula(obj.getMatricula());
        
        if (p.isEmpty())
            throw new UserNotFoundException(UserTypeEnum.USUARIO);
        
        Professor professor = p.get(); 

        return Professor.builder()
            .id(professor.getId())
            .nome(professor.getNome())
            .matricula(professor.getMatricula())
            .senha(professor.getSenha())
            .areaAtuacao(professor.getAreaAtuacao())
            .formacao(professor.getFormacao())
            .build();
    }

    private Aluno getAsAluno(Usuario obj)  throws ApiException {
        Optional<Aluno> aluno = alunoRepository.findByMatricula(obj.getMatricula());
        
        return Aluno.builder()
            .id(aluno.get().getId())
            .nome(aluno.get().getNome())
            .matricula(aluno.get().getMatricula())
            .senha(aluno.get().getSenha())
            .curso(aluno.get().getCurso())
            .build();
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Usuario obj) {
        try {
            UsuarioIF usuario = obj.getTipoUsuario() == Usuario.TIPO_PROFESSOR ? getAsProfessor(obj) : getAsAluno(obj);
            
            if (obj.getSenha().equals(usuario.getSenha())){
                return new ResponseEntity<>(usuario, HttpStatus.OK);
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (ApiException e) {
            return new ResponseEntity<>(e.getApiExceptionObject(), e.getStatus());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
