package com.backend.model;

import java.util.Calendar;

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

    public UserTypeEnum getTipoUsuario(){
        if (this.getMatricula().substring(0,2).equals("10")){
            return UserTypeEnum.ALUNO;
        }else{
            return UserTypeEnum.PROFESSOR;
        }
    }

    public static String gerarMatricula(UserTypeEnum tipoUsuario){
        Integer matricula = (int) Math.floor(Math.random() * 99999);
        return String.format("%d%04d%04d", tipoUsuario.getPrefixo(), Calendar.getInstance().get(Calendar.YEAR),matricula);
    }

}
