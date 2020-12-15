package com.backend.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColaboradorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NonNull
    private Integer idAluno;
    
    @NonNull
    private String papel;

}
