package com.gestortareas.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEntradaDTO {

    private String nombre;
    private String email;
    private String contrasenia;
}
