package com.gestortareas.tarea.service;

import com.gestortareas.tarea.client.UsuarioClient;
import com.gestortareas.tarea.dto.TareaEntradaDTO;
import com.gestortareas.tarea.dto.UsuarioDTO;
import com.gestortareas.tarea.entities.Tarea;
import com.gestortareas.tarea.mapper.TareaMapper;
import com.gestortareas.tarea.repository.TareaRepository;
import com.gestortareas.tarea.response.FullTaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TareaServiceImpl implements ITareaService{

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private TareaMapper tareaMapper;

    @Autowired
    private UsuarioClient usuarioClient;

    public Page<Tarea> findAllTasks(Pageable pageable){
        return tareaRepository.findAll(pageable);
    }

    public Tarea findTaskById(String id){
        return tareaRepository.findById(id).orElse(null);
    }

    public Tarea createTask(TareaEntradaDTO inputTask){
        Tarea task = tareaMapper.convertTareaEntradaDTOToTarea(inputTask);
        task.setFechaCreacion(new Date());
        task.setCompletada(false);
        return tareaRepository.save(task);
    }

    public Tarea updateWithFullTask(String id, TareaEntradaDTO inputTask){
        Tarea oldTask = tareaRepository.findById(id).orElse(null);
        if(oldTask != null){
            Tarea newTask = tareaMapper.convertTareaEntradaDTOToTarea(inputTask);
            newTask.setId(id);
            newTask.setFechaCreacion(oldTask.getFechaCreacion());
            newTask.setCompletada(oldTask.isCompletada());
            return tareaRepository.save(newTask);
        }else{
            return null;
        }
    }

    public boolean deleteTask(String id){
        Tarea task = tareaRepository.findById(id).orElse(null);
        if(task != null) {
            tareaRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public Tarea completeTask(String id){
        Tarea task = tareaRepository.findById(id).orElse(null);
        if(task != null) {
            task.setCompletada(true);
            return tareaRepository.save(task);
        }else{
            return null;
        }
    }

    public Tarea restartTask(String id){
        Tarea task = tareaRepository.findById(id).orElse(null);
        if(task != null) {
            task.setCompletada(false);
            return tareaRepository.save(task);
        }else{
            return null;
        }
    }

    public List<Tarea> getTasksByUserId(long userId){
        return tareaRepository.findByUsuarioId(userId);
    }

    public FullTaskResponse getFullTaskInfo(String id){
        Tarea task = tareaRepository.findById(id).orElse(null);
        UsuarioDTO user;
        if(task != null){
            user = usuarioClient.findUserById(task.getPropietario());

            return FullTaskResponse.builder()
                    .id(task.getId())
                    .nombre(task.getNombre())
                    .descripcion(task.getDescripcion())
                    .fechaCreacion(task.getFechaCreacion())
                    .completada(task.isCompletada())
                    .propietario(user)
                    .build();
        }else{
            return null;
        }
    }
}
