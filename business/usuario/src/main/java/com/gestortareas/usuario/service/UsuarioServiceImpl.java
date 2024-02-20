package com.gestortareas.usuario.service;

import com.gestortareas.usuario.dto.TareaDTO;
import com.gestortareas.usuario.dto.UsuarioEntradaDTO;
import com.gestortareas.usuario.dto.UsuarioSalidaDTO;
import com.gestortareas.usuario.entities.Usuario;
import com.gestortareas.usuario.mapper.UsuarioMapper;
import com.gestortareas.usuario.repository.UsuarioRepository;
import com.gestortareas.usuario.response.FullUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioMapper usuarioMapper;

    private final WebClient taskClient;

    public UsuarioServiceImpl(WebClient.Builder webClientBuilder) {
        this.taskClient = webClientBuilder.baseUrl("http://localhost:8091/api/tarea").build();
    }

    public Page<UsuarioSalidaDTO> findAllUsers(Pageable pageable){
        Page<Usuario> usersResult = usuarioRepository.findAll(pageable);
        return usersResult.map(usuarioMapper::convertUsuarioToUsuarioSalidaDTO);
    }

    public UsuarioSalidaDTO findUserById(long id){
        Usuario user = usuarioRepository.findById(id).orElse(null);
        return (user != null) ? usuarioMapper.convertUsuarioToUsuarioSalidaDTO(user) : null;
    }

    public UsuarioSalidaDTO findUserByName(String name){
        Usuario user = usuarioRepository.findByName(name);
        return (user != null) ? usuarioMapper.convertUsuarioToUsuarioSalidaDTO(user) : null;
    }

    public UsuarioSalidaDTO createUser(UsuarioEntradaDTO inputUser){
        Usuario resultUser = usuarioRepository.save(usuarioMapper.convertUsuarioEntradaDTOToUsuario(inputUser));
        return usuarioMapper.convertUsuarioToUsuarioSalidaDTO(resultUser);
    }

    public UsuarioSalidaDTO updateWithFullUser(long id, UsuarioEntradaDTO inputUser){
        if(usuarioRepository.findById(id).isPresent()){
            Usuario newUser = usuarioMapper.convertUsuarioEntradaDTOToUsuario(inputUser);
            newUser.setId(id);
            return usuarioMapper.convertUsuarioToUsuarioSalidaDTO(usuarioRepository.save(newUser));
        }else{
            return null;
        }
    }

    public UsuarioSalidaDTO updateWithPartialUser(long id, UsuarioEntradaDTO inputUser){
        Usuario oldUser = usuarioRepository.findById(id).orElse(null);
        if(oldUser != null){
            if(inputUser.getNombre() != null){
                oldUser.setNombre(inputUser.getNombre());
            }
            if(inputUser.getEmail() != null) {
                oldUser.setEmail(inputUser.getEmail());
            }
            if(inputUser.getContrasenia() != null) {
                oldUser.setContrasenia(inputUser.getContrasenia());
            }
            Usuario newUser = usuarioRepository.save(oldUser);
            return usuarioMapper.convertUsuarioToUsuarioSalidaDTO(newUser);
        }else{
            return null;
        }
    }

    public boolean deleteUser(long id){
        Usuario user = usuarioRepository.findById(id).orElse(null);
        if(user != null) {
            usuarioRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public FullUserResponse getFullUserInfo(long id){
        Usuario user = usuarioRepository.findById(id).orElse(null);
        List<TareaDTO> tasks;
        if(user != null){
            tasks = taskClient.get()
                    .uri("/usuario/{id}", id)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<TareaDTO>>() {})
                    .block();

            return FullUserResponse.builder()
                    .id(user.getId())
                    .nombre(user.getNombre())
                    .listaTareas(tasks)
                    .build();
        }else{
            return null;
        }
    }

}
