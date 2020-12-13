package com.backend;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.backend.dto.AlunoDTO;
import com.backend.model.Aluno;
import com.backend.repository.AlunoRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.backend.controller.AlunoController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AlunoControllerTeste {

    @SpyBean
    AlunoController controller;

    @MockBean
    AlunoRepository alunoRepository;

    @Test
    public void deveSalvarAluno() {
        Aluno aluno = Aluno.builder().nome("Thairam").senha("senha").curso("computação").matricula("matricula").build();
        Mockito.when(alunoRepository.save(Mockito.any(Aluno.class))).thenReturn(aluno);
        ResponseEntity<?> resposta = controller.insert(new AlunoDTO(aluno));
        
        Aluno alunoSalvo = (Aluno) resposta.getBody();
        
        System.out.println("ALUNO: " + alunoSalvo);
        assertEquals(resposta.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(alunoSalvo.getId());
        assertNotNull(alunoSalvo.getMatricula());
        assertEquals(alunoSalvo.getNome(), "Thairam");
        assertEquals(alunoSalvo.getCurso(), "computação");
    }
}