package com.rediense.todoapp.service;

import com.rediense.todoapp.dto.TareaDTO;
import com.rediense.todoapp.exception.DataNotFoundException;
import com.rediense.todoapp.mapper.TareaMapper;
import com.rediense.todoapp.model.Tarea;
import com.rediense.todoapp.repository.TareaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TareaService {

    private final Logger log = LoggerFactory.getLogger(TareaService.class);
    private final TareaRepository tareaRepository;
    private final TareaMapper tareaMapper;

    public TareaService(TareaRepository tareaRepository, TareaMapper tareaMapper) {
        this.tareaRepository = tareaRepository;
        this.tareaMapper = tareaMapper;
    }

    private void validateTareaDTO(TareaDTO tareaDTO) {
        if (tareaDTO.getTitulo().isEmpty()) {
            log.info("La tarea debe tener un título");
            throw new IllegalArgumentException("El título de la tarea no puede estar vacío");
        }
    }

    // Para crear y actualizar
    public TareaDTO saveTarea(TareaDTO tareaDTO){
        validateTareaDTO(tareaDTO);

        Tarea tarea = tareaMapper.toTarea(tareaDTO);
        tarea = tareaRepository.save(tarea);
        return tareaMapper.toTareaDTO(tarea);
    }

    public TareaDTO getTarea(Long id){
        Optional<Tarea> optionalTarea = tareaRepository.findById(id);

        if(optionalTarea.isEmpty()){
            log.info("No existe la tarea de id: {}", id);
            throw new DataNotFoundException("La tarea no existe");
        } else {
            Tarea tarea = optionalTarea.get();
            return tareaMapper.toTareaDTO(tarea);

        }
    }

    public List<TareaDTO> getTareas(){
        List<Tarea> tareaList = tareaRepository.findAll();

        if(tareaList.isEmpty()){
            log.info("Lista de tareas vacía");
            return new ArrayList<>();
        }

        return tareaList.stream()
                .map(tareaMapper::toTareaDTO)
                .toList();
    }

    public void deleteTarea(Long id){
        Optional<Tarea> optionalTarea = tareaRepository.findById(id);
        if(optionalTarea.isEmpty()){
            log.info("Tarea con id: {} no encontrada", id);
            throw new DataNotFoundException("La Tarea no existe");
        } else {
            tareaRepository.deleteById(id);
        }
    }

    public List<TareaDTO> searchTareasByTitulo(String term){
        List<Tarea> tareaList = tareaRepository.findAllByTituloContainingIgnoreCase(term);

        return tareaList.stream()
                .map(tareaMapper::toTareaDTO)
                .toList();
    }
}
