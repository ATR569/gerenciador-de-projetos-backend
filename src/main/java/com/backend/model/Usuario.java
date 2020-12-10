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
    
    @NonNull
    private String matricula;
    
    @NonNull
    private String nome;
    
    @NonNull
    private String senha;
}
