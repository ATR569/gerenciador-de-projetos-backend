package com.backend;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.backend.controller.AlunoController;
import com.backend.model.Aluno;
import com.backend.repository.AlunoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTeste {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    AlunoController controller;

    @MockBean
    AlunoRepository alunoRepository;

    private List<Aluno> alunosLista;

    @BeforeEach
    void setUp() {
        this.alunosLista = new ArrayList<>();
        this.alunosLista.add(new Aluno(1, "10123", "Thairam", "senha", "Computação"));
        this.alunosLista.add(new Aluno(2, "10456", "Ramon", "senha", "Computação"));
        this.alunosLista.add(new Aluno(1, "10789", "Adson", "senha", "Computação"));
    }
    
    @Test
    public void deveListarAlunos() throws Exception {
        given(alunoRepository.findAll()).willReturn(this.alunosLista);

        this.mockMvc.perform(get("/api/alunos")).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(this.alunosLista.size())));
    }

    @Test
    public void deveEncontrarAluno() throws Exception {
        Aluno aluno = Aluno.builder().id(1).nome("Thairam").senha("senha").curso("computação").matricula("matricula")
                .build();
        given(alunoRepository.findById(1)).willReturn(Optional.of(aluno));
        
        this.mockMvc.perform(get("/api/alunos/{id}", 1)).andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(aluno.getNome())));
    }

    @Test
    public void naoDeveObterAluno() {
        ResponseEntity<?> respostaGet = controller.getAlunoById(1);
        assertEquals(respostaGet.getStatusCode(), HttpStatus.NOT_FOUND);
    }

}