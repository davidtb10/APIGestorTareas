package com.gestortareas.usuario.mapper;

import com.gestortareas.usuario.dto.UsuarioEntradaDTO;
import com.gestortareas.usuario.dto.UsuarioSalidaDTO;
import com.gestortareas.usuario.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario convertUsuarioEntradaDTOToUsuario(UsuarioEntradaDTO usuarioEntrada){
        return new Usuario(0, usuarioEntrada.getNombre(), usuarioEntrada.getEmail(), usuarioEntrada.getContrasenia());
    }

    public UsuarioSalidaDTO convertUsuarioToUsuarioSalidaDTO(Usuario usuario){
        return new UsuarioSalidaDTO(usuario.getId(), usuario.getNombre(), usuario.getEmail());
    }
}
