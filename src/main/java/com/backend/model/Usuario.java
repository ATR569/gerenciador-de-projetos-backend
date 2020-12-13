package com.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
    public static final int TIPO_PROFESSOR = 1;
    public static final int TIPO_ALUNO = 2;
    
    @NonNull
    String matricula;
    
    @NonNull
    String senha;

    public int getTipoUsuario(){
        if (this.getMatricula().substring(0,2).equals("10")){
            return TIPO_ALUNO;
        }else{
            return TIPO_PROFESSOR;
        }
    }
}
