package com.gestortareas.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TareaDTO {

    private String id;
    private String nombre;
    private String descripcion;
    private Date fechaCreacion;
    private boolean completada;
}
