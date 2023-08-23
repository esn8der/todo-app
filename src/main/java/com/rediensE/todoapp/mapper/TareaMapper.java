package com.rediense.todoapp.mapper;

import com.rediense.todoapp.dto.TareaDTO;
import com.rediense.todoapp.model.Tarea;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TareaMapper {
    private final ModelMapper modelMapper;

    public TareaMapper() {
        this.modelMapper = new ModelMapper();
        configureMappings();
    }

    private void configureMappings(){
        modelMapper.createTypeMap(Tarea.class, TareaDTO.class)
                .addMapping(Tarea::getId, TareaDTO::setId)
                .addMapping(Tarea::getTitulo, TareaDTO::setTitulo)
                .addMapping(Tarea::getDescripcion, TareaDTO::setDescripcion)
                .addMapping(Tarea::getEstado, TareaDTO::setEstado)
                .addMapping(Tarea::isFavorito, TareaDTO::setFavorito);
    }

    public TareaDTO toTareaDTO(Tarea tarea){
        return modelMapper.map(tarea, TareaDTO.class);
    }

    public Tarea toTarea(TareaDTO tareaDTO){
        return modelMapper.map(tareaDTO, Tarea.class);
    }
}
