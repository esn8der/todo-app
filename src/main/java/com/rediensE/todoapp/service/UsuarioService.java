package com.rediense.todoapp.service;

import com.rediense.todoapp.dto.UsuarioDTO;
import com.rediense.todoapp.exception.DataNotFoundException;
import com.rediense.todoapp.mapper.UsuarioMapper;
import com.rediense.todoapp.model.Usuario;
import com.rediense.todoapp.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final Logger log = LoggerFactory.getLogger(UsuarioService.class);
    private static final String USUARIO_NOT_FOUND = "No se encontró el usuario con ID: {}";


    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioDTO getUsuario(Long id){
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if(optionalUsuario.isEmpty()){
            log.info(USUARIO_NOT_FOUND, id);
            throw new DataNotFoundException("Usuario no encontrado");
        } else {
            Usuario user = optionalUsuario.get();
            return usuarioMapper.toUsuarioDTO(user);
        }
    }

    public List<UsuarioDTO> getUsuarios() {
        List<Usuario> users = usuarioRepository.findAll();

        if (users.isEmpty()){
            log.info("Lista de usuarios vacía");
            throw new DataNotFoundException("No hay usuarios creados");
        } else {
            List<UsuarioDTO> usersDTO = new ArrayList<>();
            for (Usuario user : users) {
                usersDTO.add(usuarioMapper.toUsuarioDTO(user));
            }
            return usersDTO;
        }
    }


}
