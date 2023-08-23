package com.rediense.todoapp.mapper;

import com.rediense.todoapp.dto.UsuarioDTO;
import com.rediense.todoapp.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    private final ModelMapper modelMapper;

    public UsuarioMapper() {
        this.modelMapper = new ModelMapper();
        configureMappings();
    }

    private void configureMappings() {
        modelMapper.createTypeMap(Usuario.class, UsuarioDTO.class)
                .addMapping(Usuario::getId, UsuarioDTO::setId)
                .addMapping(Usuario::getNombre, UsuarioDTO::setNombre);
    }

    public UsuarioDTO toUsuarioDTO(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public Usuario toUsuario(UsuarioDTO usuarioDTO) {
        return modelMapper.map(usuarioDTO, Usuario.class);
    }

}
