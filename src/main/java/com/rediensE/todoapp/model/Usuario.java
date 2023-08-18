package com.rediense.todoapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long id;

    @Column()
    private String nombre;

    @Column()
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Tarea> tareas;

}
