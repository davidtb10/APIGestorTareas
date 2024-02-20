package com.gestortareas.usuario.entities;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Usuario")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Id autogenerado por la base de datos")
    private long id;
    @Schema(description = "Nickname del usuario")
    private String nombre;
    @Schema(description = "Dirección de email")
    private String email;
    @Schema(description = "Contraseña del usuario")
    private String contrasenia;
}
