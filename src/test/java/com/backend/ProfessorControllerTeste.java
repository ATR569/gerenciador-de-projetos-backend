package com.backend;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.backend.controller.ProfessorController;
import com.backend.dto.ProfessorDTO;
import com.backend.model.Professor;
import com.backend.repository.ProfessorRepository;
import com.backend.repository.ProjetoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
//import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
// @WebMvcTest(controllers = ProfessorController.class)
public class ProfessorControllerTeste {

        @Autowired
        private WebApplicationContext webApplicationContext;

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper ObjectMapper;

        // @MockBean
        // private ProfessorController professorController;

        @MockBean
        private ProfessorRepository professorRepository;

        @MockBean
        private ProjetoRepository projetoRepository;

        @BeforeEach
        public void setup() {
                mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        }

        @Test
        public void deveSalvarComSucesso() throws Exception {
                Professor professor = Professor.builder().id(1).nome("Ramon").senha("12234").matricula("12345as1")
                                .areaAtuacao("Pesquisador").formacao("Mestrado CC").build();

                given(professorRepository.save(professor)).willReturn(professor);

                String result = this.mockMvc.perform(post("/api/professores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{'nome': 'Ramon', 'senha': '12345', 'areaAtuacao': 'Pesquisador', 'formacao': 'Mestrado'}"))
                        .andExpect(status().isCreated())
                        .andReturn().getResponse().getContentAsString();

                System.out.println(result);
        }

        public void naoDeveSalvar() {

        }

        @Test
        public void deveListarProfessores() throws Exception {
                List<Professor> listaProfessores = new ArrayList<>();
                listaProfessores.add(new Professor(1, "15871221", "Joao", "12123", "Pesquisador", "Bacharel"));

                given(professorRepository.findAll()).willReturn(listaProfessores);

                // String result = this.mockMvc
                this.mockMvc.perform(get("/api/professores")).andExpect(status().isOk());
                // .andReturn().getResponse().getContentAsString();
                // .andExpect(content().json("[{'id':1, 'matricula': '15871221', 'nome': 'Joao',
                // 'areaAtuacao': 'Pesquisador', 'formacao': 'Bacharel'}]"));
                // .andExpect(jsonPath("$[0].nome", is(listaProfessores.get(0).getNome())));
                // .andExpect(jsonPath("$[0].nome").value("Joao"));

                // System.out.println(result);
        }

        @Test
        public void deveListarUmProfessor() throws Exception {
                Professor professor = Professor.builder().id(1).nome("Ramon").senha("1234").matricula("matricula")
                                .areaAtuacao("Pesquisador").formacao("Mestre").build();

                professorRepository.save(professor);

                given(professorRepository.findById(1)).willReturn(Optional.of(professor));

                this.mockMvc.perform(get("/api/professores/{id}", 1)).andExpect(status().isOk());
                // .andExpect(jsonPath("$.nome").value("Ramon"));
                // .andExpect(MockMvcResultMatchers.jsonPath("$.areaAtuacao",
                // is(professor.getAreaAtuacao())))
                // .andExpect(MockMvcResultMatchers.jsonPath("$.formacao",
                // is(professor.getFormacao())));
        }

        @Test
        public void naoDeveListarUmProfessor() throws Exception {
                Professor professor = Professor.builder().id(1).nome("Ramon").senha("1234").matricula("matricula")
                                .areaAtuacao("Pesquisador").formacao("Mestre").build();

                professorRepository.save(professor);

                given(professorRepository.findById(1)).willReturn(Optional.of(professor));

                this.mockMvc.perform(get("/api/professores/{id}", 10)).andExpect(status().isNotFound());
        }

}
