package com.backend.utils;

import com.backend.model.UserTypeEnum;

public abstract class UsuarioUtils {
 
    public static UserTypeEnum getTipoUsuario(String matricula) {
        if (matricula.substring(0, 2).equals("10")) {
            return UserTypeEnum.ALUNO;
        } else {
            return UserTypeEnum.PROFESSOR;
        }
    }
}