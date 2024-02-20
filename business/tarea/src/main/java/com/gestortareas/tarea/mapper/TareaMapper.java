package com.gestortareas.tarea.mapper;

import com.gestortareas.tarea.dto.TareaEntradaDTO;
import com.gestortareas.tarea.entities.Tarea;
import org.springframework.stereotype.Component;

@Component
public class TareaMapper {

    public Tarea convertTareaEntradaDTOToTarea(TareaEntradaDTO tareaEntradaDTO){
        return new Tarea(null, tareaEntradaDTO.getNombre(), tareaEntradaDTO.getDescripcion(), null, false, tareaEntradaDTO.getPropietario());
    }
}
