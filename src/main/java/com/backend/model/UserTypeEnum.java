package com.backend.model;

public enum UserTypeEnum {
    ALUNO(10, "Aluno"), PROFESSOR(20, "Professor"), USUARIO(0, "Usuário");

    private final int prefixo;
    private String descricao;

    public int getPrefixo(){
        return this.prefixo;
    }

    public String getDescricao(){
        return this.descricao;
    }

    private UserTypeEnum(int prefixo, String descricao){
        this.prefixo = prefixo;
        this.descricao = descricao;
    }
}
