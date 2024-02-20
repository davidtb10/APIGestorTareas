package com.gestortareas.tarea.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TareaEntradaDTO {

    private String nombre;
    private String descripcion;
    private long propietario;
}
