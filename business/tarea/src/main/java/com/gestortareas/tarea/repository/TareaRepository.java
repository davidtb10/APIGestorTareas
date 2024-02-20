package com.gestortareas.tarea.repository;

import com.gestortareas.tarea.entities.Tarea;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TareaRepository extends MongoRepository<Tarea, String> {

    @Query("{ 'usuario_id' : ?0 }")
    public List<Tarea> findByUsuarioId(long usuarioId);
}
