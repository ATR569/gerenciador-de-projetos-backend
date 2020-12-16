package com.backend.model;

import java.util.Calendar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import com.backend.utils.UsuarioUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
    public static final int TIPO_PROFESSOR = 1;
    public static final int TIPO_ALUNO = 2;

    @NonNull
    String username;

    @NonNull
    String senha;

    public UserTypeEnum getTipoUsuario() {
        return UsuarioUtils.getTipoUsuario(this.getUsername());
    }

    public static String gerarMatricula(UserTypeEnum tipoUsuario) {
        Integer matricula = (int) Math.floor(Math.random() * 99999);
        return String.format("%02d%04d%05d", tipoUsuario.getPrefixo(), Calendar.getInstance().get(Calendar.YEAR),
                matricula);
    }

}
