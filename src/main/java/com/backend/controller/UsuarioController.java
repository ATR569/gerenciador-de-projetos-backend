package com.backend.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.backend.exceptions.ApiException;
import com.backend.exceptions.EmptyBodyException;
import com.backend.exceptions.RequiredFieldsException;
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

    private Professor getAsProfessor(Usuario obj) throws ApiException {
        Optional<Professor> p = professorRepository.findByMatricula(obj.getMatricula());

        if (p.isEmpty())
            throw new UserNotFoundException(UserTypeEnum.USUARIO);

        return p.get();
    }

    private Aluno getAsAluno(Usuario obj) throws ApiException {
        Optional<Aluno> a = alunoRepository.findByMatricula(obj.getMatricula());

        if (a.isEmpty())
            throw new UserNotFoundException(UserTypeEnum.USUARIO);

        return a.get();
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Usuario obj) {
        try {
            validarUsuario(obj);
            UsuarioIF usuario = obj.getTipoUsuario() == UserTypeEnum.PROFESSOR ? getAsProfessor(obj) : getAsAluno(obj);

            if (obj.getSenha().equals(usuario.getSenha())) {
                return new ResponseEntity<>(usuario, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (ApiException e) {
            return new ResponseEntity<>(e.getApiExceptionObject(), e.getStatus());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private void validarUsuario(Usuario usuario) throws ApiException {
        if (usuario == null)
            throw new EmptyBodyException();

        List<String> emptyFields = new ArrayList<>();

        if (usuario.getMatricula() == null)
            emptyFields.add("matricula");
        if (usuario.getSenha() == null)
            emptyFields.add("senha");

        if (emptyFields.size() > 0)
            throw new RequiredFieldsException(emptyFields.toArray());
    }

}
