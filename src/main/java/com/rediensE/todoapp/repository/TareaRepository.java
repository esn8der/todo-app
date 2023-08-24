package com.rediense.todoapp.repository;

import com.rediense.todoapp.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findAllByTituloContainingIgnoreCase(String term);
}
