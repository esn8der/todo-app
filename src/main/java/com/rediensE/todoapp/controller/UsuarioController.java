package com.rediense.todoapp.controller;

import com.rediense.todoapp.dto.UsuarioDTO;
import com.rediense.todoapp.exception.RegistrationException;
import com.rediense.todoapp.model.Usuario;
import com.rediense.todoapp.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@ApiResponse(responseCode = "400", description = "Petición inválida")
@ApiResponse(responseCode = "500", description = "Error interno al procesar la petición.", content = { @Content(schema = @Schema()) })
@RestController
@RequestMapping("/v1/usuarios")
@Tag(name = "Usuario")
public class UsuarioController {
    private final Logger log = LoggerFactory.getLogger(UsuarioController.class);
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(
            summary = "Regresa un usuario por id.",
            description = "Obtiene un objeto Usuario por un id especifico. La respuesta es un objeto Usuario con id y nombre.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Petición exitosa"),
                    @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable Long id) {
        log.info("Request GET /v1/usuarios/{} - Buscando usuario por id", id);
        UsuarioDTO usuarioDTO = usuarioService.getUsuario(id);
        return ResponseEntity.ok(usuarioDTO);
    }

    @Operation(
            summary = "Regresa todos los usuarios.",
            description = "Obtiene una Lista de objetos Usuario, puede estar vacía.",
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(schema = @Schema(implementation = Usuario.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "204", description = "No hay usuarios", content = {
                            @Content(schema = @Schema())})
            }
    )
    @GetMapping("")
    public ResponseEntity<List<UsuarioDTO>> getUsuarios() {
        log.info("Request GET /v1/usuarios - Buscando todos los usuarios");
        List<UsuarioDTO> usuarioDTOS = usuarioService.getUsuarios();
        return ResponseEntity.ok(usuarioDTOS);
    }

    @Operation(
            summary = "Registrar un usuario.",
            description = "Registra un objeto usuario en la base de dato siempre que cumpla con los requisitos",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuario registrado con éxito."),
                    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta debido a datos inválidos o faltantes."),
                    @ApiResponse(responseCode = "409", description = "Conflicto: El usuario ya existe."),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
            }
    )
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestParam String name, @RequestParam String pass){
        try{
            UsuarioDTO registeredUser = usuarioService.registrarUsuario(name, pass);
            return ResponseEntity.ok(registeredUser);
        } catch (RegistrationException e){
            return ResponseEntity.badRequest().body(new UsuarioDTO());
        }
    }
}
