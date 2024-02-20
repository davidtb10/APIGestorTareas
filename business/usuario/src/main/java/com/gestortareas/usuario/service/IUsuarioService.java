package com.gestortareas.usuario.service;

import com.gestortareas.usuario.dto.UsuarioEntradaDTO;
import com.gestortareas.usuario.dto.UsuarioSalidaDTO;
import com.gestortareas.usuario.entities.Usuario;
import com.gestortareas.usuario.response.FullUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IUsuarioService {

    Page<UsuarioSalidaDTO> findAllUsers(Pageable pageable);
    UsuarioSalidaDTO findUserById(long id);
    UsuarioSalidaDTO findUserByName(String name);
    UsuarioSalidaDTO createUser(UsuarioEntradaDTO user);
    UsuarioSalidaDTO updateWithFullUser(long id, UsuarioEntradaDTO newUser);
    UsuarioSalidaDTO updateWithPartialUser(long id, UsuarioEntradaDTO newUser);
    boolean deleteUser(long id);
    FullUserResponse getFullUserInfo(long id);

}
