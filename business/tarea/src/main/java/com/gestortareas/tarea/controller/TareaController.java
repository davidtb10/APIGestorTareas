package com.gestortareas.tarea.controller;

import com.gestortareas.tarea.dto.TareaEntradaDTO;
import com.gestortareas.tarea.entities.Tarea;
import com.gestortareas.tarea.mapper.TareaMapper;
import com.gestortareas.tarea.response.FullTaskResponse;
import com.gestortareas.tarea.service.ITareaService;
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


import java.util.List;

@RestController
@RequestMapping("/api/tarea")
@Tag(name = "Tarea")
public class TareaController {

    @Autowired
    private ITareaService tareaService;

    @GetMapping("/")
    @Operation(summary = "Listar tareas", description = "Esta operación devuelve el listado de tareas existentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Tarea.class))))
    })
    public ResponseEntity<?> findallTasks(@PageableDefault(page = 0, size = 5) Pageable pageable){
        return ResponseEntity.ok(tareaService.findAllTasks(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tarea por id", description = "Esta operación busca una tarea por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito", content = @Content(schema = @Schema(implementation = Tarea.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> findById(@PathVariable String id){
        Tarea task = tareaService.findTaskById(id);
        return (task != null) ? ResponseEntity.ok(task) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    @Operation(summary = "Crear tarea", description = "Esta operación crea una nueva tarea.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creado", content = @Content(schema = @Schema(implementation = Tarea.class)))
    })
    public ResponseEntity<?> save(@RequestBody TareaEntradaDTO task){
        return new ResponseEntity<>(tareaService.createTask(task), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tarea", description = "Esta operación permite actualizar una tarea existente. Los campos no asignados se rellenarán a null")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito", content = @Content(schema = @Schema(implementation = Tarea.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> putUpdate(@PathVariable String id, @RequestBody TareaEntradaDTO task){
        Tarea taskUpdated = tareaService.updateWithFullTask(id, task);
        return (taskUpdated != null) ? ResponseEntity.ok(taskUpdated) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tarea", description = "Esta operación elimina una tarea existente a través de su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Éxito", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> delete(@PathVariable String id){
        return (tareaService.deleteTask(id)) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/completar")
    @Operation(summary = "Completar tarea", description = "Esta operación marca la tarea como completada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito", content = @Content(schema = @Schema(implementation = Tarea.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> complete(@PathVariable String id){
        Tarea taskUpdated = tareaService.completeTask(id);
        return (taskUpdated != null) ? ResponseEntity.ok(taskUpdated) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/descompletar")
    @Operation(summary = "Descompletar tarea", description = "Esta operación desmarca la tarea como completada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito", content = @Content(schema = @Schema(implementation = Tarea.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> restart(@PathVariable String id){
        Tarea taskUpdated = tareaService.restartTask(id);
        return (taskUpdated != null) ? ResponseEntity.ok(taskUpdated) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/usuario/{id}")
    @Operation(summary = "Buscar tarea por id de propietario", description = "Esta operación busca una tarea por el id de su propietario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Tarea.class)))),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> findByUserId(@PathVariable long id){
        List<Tarea> taskList = tareaService.getTasksByUserId(id);
        return (taskList != null) ? ResponseEntity.ok(taskList) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/completo")
    @Operation(summary = "Buscar tarea por id con toda su información", description = "Esta operación recupera toda la información de una tarea (incluyendo información del propietario) buscándola por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito", content = @Content(schema = @Schema(implementation = FullTaskResponse.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> findFullTask(@PathVariable String id){
        FullTaskResponse task = tareaService.getFullTaskInfo(id);
        return (task != null) ? ResponseEntity.ok(task) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
