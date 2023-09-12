package com.rediense.todoapp.service;

import com.rediense.todoapp.dto.UsuarioDTO;
import com.rediense.todoapp.exception.DataNotFoundException;
import com.rediense.todoapp.exception.RegistrationException;
import com.rediense.todoapp.mapper.UsuarioMapper;
import com.rediense.todoapp.model.Usuario;
import com.rediense.todoapp.repository.UsuarioRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
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
            return new ArrayList<>();
        }

        return users.stream()
                .map(usuarioMapper::toUsuarioDTO)
                .toList();
    }

    public UsuarioDTO registrarUsuario(String nombre, String password){
        Optional<Usuario> optionalUsuario = Optional.ofNullable(usuarioRepository.findByNombre(nombre));

        if(optionalUsuario.isPresent()){
            log.info("El usuario ya existe");
            throw new RegistrationException("El usuario ya está registrado");
        }
        if (!nombre.matches("^[a-zA-Z0-9]{2,}$")) {
            log.info("Nombre invalido");
            throw new RegistrationException("El nombre no cumple con los requisitos.");
        }
        if (!isValidPassword(password)) {
            log.info("Contraseña invalida");
            throw new RegistrationException("La contraseña no cumple con los requisitos.");
        }

        Usuario usuario = new Usuario();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hashedPassword = argon2.hash(1, 1024, 1, password);

        usuario.setPassword(hashedPassword);
        usuario.setNombre(nombre);


        return usuarioMapper.toUsuarioDTO(usuarioRepository.save(usuario));
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }
}
