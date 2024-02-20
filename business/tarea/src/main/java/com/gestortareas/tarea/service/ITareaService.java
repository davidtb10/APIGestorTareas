package com.gestortareas.tarea.service;

import com.gestortareas.tarea.dto.TareaEntradaDTO;
import com.gestortareas.tarea.entities.Tarea;
import com.gestortareas.tarea.response.FullTaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITareaService {

    Page<Tarea> findAllTasks(Pageable pageable);
    Tarea findTaskById(String id);
    Tarea createTask(TareaEntradaDTO task);
    Tarea updateWithFullTask(String id, TareaEntradaDTO newTask);
    boolean deleteTask(String id);
    Tarea completeTask(String id);
    Tarea restartTask(String id);
    List<Tarea> getTasksByUserId(long userId);
    FullTaskResponse getFullTaskInfo(String id);

}
