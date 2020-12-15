package com.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.backend.model.Aluno;
import com.backend.model.Professor;
import com.backend.model.UserTypeEnum;
import com.backend.model.UsuarioIF;
import com.backend.repository.AlunoRepository;
import com.backend.repository.ProfessorRepository;
import com.backend.utils.UsuarioUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioIF user;

        if (UsuarioUtils.getTipoUsuario(username) == UserTypeEnum.ALUNO){
            user = findAlunoByMatricula(username);
        }else{
            user = findProfessorByMatricula(username);
        }

        List<GrantedAuthority> permissions = new ArrayList<>();
        
        if (UsuarioUtils.getTipoUsuario(username) == UserTypeEnum.ALUNO){
            permissions.add(new SimpleGrantedAuthority("ALUNO"));
        }else{
            permissions.add(new SimpleGrantedAuthority("PROFESSOR"));
        }

        User u = new User(user.getMatricula(), user.getSenha(), permissions);

        return u;
    }

    private Aluno findAlunoByMatricula(String matricula) throws UsernameNotFoundException {
        Optional<Aluno> aluno = alunoRepository.findByMatricula(matricula);

        if (aluno == null)
            throw new UsernameNotFoundException("Usuário não encontrado!");

        return aluno.get();
    }

    private Professor findProfessorByMatricula(String matricula) throws UsernameNotFoundException {
        Optional<Professor> professor = professorRepository.findByMatricula(matricula);

        if (professor == null)
            throw new UsernameNotFoundException("Usuário não encontrado!");

        return professor.get();
    }
}