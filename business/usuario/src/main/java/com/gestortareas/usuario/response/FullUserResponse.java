package com.gestortareas.usuario.response;

import com.gestortareas.usuario.dto.TareaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FullUserResponse {

    private long id;
    private String nombre;
    private List<TareaDTO> listaTareas;
}
