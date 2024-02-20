package com.gestortareas.tarea.response;

import com.gestortareas.tarea.dto.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FullTaskResponse {
    private String id;
    private String nombre;
    private String descripcion;
    private Date fechaCreacion;
    private boolean completada;
    private UsuarioDTO propietario;

}
