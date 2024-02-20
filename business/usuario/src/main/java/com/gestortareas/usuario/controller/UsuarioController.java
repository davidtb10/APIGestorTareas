package com.gestortareas.usuario.controller;

import com.gestortareas.usuario.dto.UsuarioEntradaDTO;
import com.gestortareas.usuario.dto.UsuarioSalidaDTO;
import com.gestortareas.usuario.entities.Usuario;
import com.gestortareas.usuario.response.FullUserResponse;
import com.gestortareas.usuario.service.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/usuario")
@Tag(name = "Usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/")
    @Operation(summary = "Listar usuarios", description = "Esta operación devuelve el listado de usuarios existentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioSalidaDTO.class))))
    })
    public ResponseEntity<?> findallUsers(@PageableDefault(page = 0, size = 5) Pageable pageable){
        return ResponseEntity.ok(usuarioService.findAllUsers(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario por id", description = "Esta operación busca un usuario por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito", content = @Content(schema = @Schema(implementation = UsuarioSalidaDTO.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> findById(@PathVariable long id){
        UsuarioSalidaDTO user = usuarioService.findUserById(id);
        return (user != null) ? ResponseEntity.ok(user) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/name")
    @Operation(summary = "Buscar usuario por nombre", description = "Esta operación busca un usuario por su nombre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito", content = @Content(schema = @Schema(implementation = UsuarioSalidaDTO.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> findByName(@RequestParam String name){
        UsuarioSalidaDTO user = usuarioService.findUserByName(name);
        return (user != null) ? ResponseEntity.ok(user) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    @Operation(summary = "Crear usuario", description = "Esta operación crea un nuevo usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creado", content = @Content(schema = @Schema(implementation = UsuarioSalidaDTO.class)))
    })
    public ResponseEntity<?> save(@RequestBody UsuarioEntradaDTO user){
        return new ResponseEntity<>(usuarioService.createUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario completo", description = "Esta operación permite actualizar un usuario existente recibiendo todos sus datos. Los datos no recibidos serán asignados a null.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito", content = @Content(schema = @Schema(implementation = UsuarioSalidaDTO.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> putUpdate(@PathVariable long id, @RequestBody UsuarioEntradaDTO user){
        UsuarioSalidaDTO userUpdated = usuarioService.updateWithFullUser(id, user);
        return (userUpdated != null) ? ResponseEntity.ok(userUpdated) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar usuario parcialmente", description = "Esta operación actualiza un usuario existente pudiendo recibir parte de sus datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito", content = @Content(schema = @Schema(implementation = UsuarioSalidaDTO.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> patchUpdate(@PathVariable long id, @RequestBody UsuarioEntradaDTO user){
        UsuarioSalidaDTO userUpdated = usuarioService.updateWithPartialUser(id, user);
        return (userUpdated != null) ? ResponseEntity.ok(userUpdated) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Esta operación elimina un usuario existente a través de su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Éxito", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> delete(@PathVariable long id){
        return (usuarioService.deleteUser(id)) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/completo")
    @Operation(summary = "Buscar usuario por id con toda su información", description = "Esta operación recupera toda la información de un usuario (incluyendo sus tareas) buscándolo por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito", content = @Content(schema = @Schema(implementation = FullUserResponse.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> findFullUser(@PathVariable long id){
        FullUserResponse usuario = usuarioService.getFullUserInfo(id);
        return (usuario != null) ? ResponseEntity.ok(usuario) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
