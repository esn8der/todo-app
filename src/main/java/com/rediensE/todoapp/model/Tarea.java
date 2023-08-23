package com.rediense.todoapp.model;

import com.rediense.todoapp.utils.EstadoTarea;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tarea")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String titulo;

    @Column()
    private String descripcion;

    @Column()
    private boolean favorito;

    @Column()
    @Enumerated(EnumType.STRING) // Indica que estás utilizando un Enum
    private EstadoTarea estado; // Cambiado a usar el Enum EstadoTarea

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
