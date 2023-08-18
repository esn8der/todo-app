package com.rediense.todoapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tarea")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private String estado;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
