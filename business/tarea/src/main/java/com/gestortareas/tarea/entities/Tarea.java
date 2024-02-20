package com.gestortareas.tarea.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = "Tarea")
@AllArgsConstructor
@NoArgsConstructor
public class Tarea {

    @Id
    private String id;
    private String nombre;
    private String descripcion;
    @Field("fecha_creacion")
    private Date fechaCreacion;
    private boolean completada;
    @Field("usuario_id")
    private long propietario;
}
