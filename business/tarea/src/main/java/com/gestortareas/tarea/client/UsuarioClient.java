package com.gestortareas.tarea.client;

import com.gestortareas.tarea.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-usuario", url = "localhost:8090/api/usuario")
public interface UsuarioClient {

    @GetMapping("/{id}")
    UsuarioDTO findUserById(@PathVariable long id);
}
