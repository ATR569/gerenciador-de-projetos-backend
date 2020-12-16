package com.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.backend.dto.JwtResponseDTO;
import com.backend.exceptions.ApiException;
import com.backend.exceptions.ApiExceptionObject;
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
import com.backend.security.JwtTokenUtil;
import com.backend.service.JwtUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtAuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtService;

    @Autowired
    private AlunoRepository alunoRepository;    

    @Autowired
    private ProfessorRepository professorRepository;    

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Usuario authRequest) {
        try {
            validarUsuario(authRequest);
            final UserDetails userDetails = jwtService.loadUserByUsername(authRequest.getUsername());
            UsuarioIF usuario = authRequest.getTipoUsuario() == UserTypeEnum.PROFESSOR ? getAsProfessor(authRequest) : getAsAluno(authRequest);
            
            if (userDetails == null || !userDetails.getPassword().equals(authRequest.getSenha())) {
                ApiExceptionObject exception = new ApiExceptionObject(401, "Usuário e senha não conferem", "Não Autorizado");
                return new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED);
            }

            final String token = jwtTokenUtil.generateToken(userDetails, usuario);

            return ResponseEntity.ok(new JwtResponseDTO(token));
        } catch (ApiException e) {
            return new ResponseEntity<>(e.getApiExceptionObject(), e.getStatus());
        } catch (Exception e) {
            ApiExceptionObject exception = new ApiExceptionObject(401, "Usuário não encontrado", "Não Autorizado");
            return new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED);
        }
    }

    private void validarUsuario(Usuario usuario) throws ApiException {
        if (usuario == null)
            throw new EmptyBodyException();

        List<String> emptyFields = new ArrayList<>();

        if (usuario.getUsername() == null)
            emptyFields.add("username");
        if (usuario.getSenha() == null)
            emptyFields.add("senha");

        if (emptyFields.size() > 0)
            throw new RequiredFieldsException(emptyFields.toArray());
    }

    private Professor getAsProfessor(Usuario obj) throws ApiException {
        Optional<Professor> p = professorRepository.findByMatricula(obj.getUsername());

        if (p == null)
            throw new UserNotFoundException(UserTypeEnum.USUARIO);

        return p.get();
    }

    private Aluno getAsAluno(Usuario obj) throws ApiException {
        Optional<Aluno> a = alunoRepository.findByMatricula(obj.getUsername());

        if (a == null)
            throw new UserNotFoundException(UserTypeEnum.USUARIO);

        return a.get();
    }
}