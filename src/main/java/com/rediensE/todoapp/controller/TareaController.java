package com.rediense.todoapp.controller;

import com.rediense.todoapp.dto.TareaDTO;
import com.rediense.todoapp.model.Tarea;
import com.rediense.todoapp.service.TareaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
@RequestMapping("/v1/tareas")
@Tag(name = "Tarea")
public class TareaController {
    private final Logger log = LoggerFactory.getLogger(TareaController.class);
    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @Operation(
            summary = "Agregar una tarea nueva",
            description = "Se agrega una tarea nueva al usuario correspondiente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Petición exitosa"),
                    @ApiResponse(responseCode = "201", description = "Tarea creada")
            }
    )
    @PostMapping("/create")
    public ResponseEntity<TareaDTO> createTarea(@RequestBody TareaDTO tareaDTO) throws URISyntaxException {
        log.info("Request POST /v1/tareas/create - Agregando tarea de titulo: {}", tareaDTO.getTitulo());
        TareaDTO newTareaDTO = tareaService.saveTarea(tareaDTO);
        //return ResponseEntity.ok(newTareaDTO);
        return ResponseEntity.created(new URI("/v1/tareas/" + newTareaDTO.getId())).body(newTareaDTO);
    }

    @Operation(
            summary = "Actualizar una tarea",
            description = "Se actualiza una tarea por su id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Petición exitosa")
            }
    )
    @PutMapping("/update")
    public ResponseEntity<TareaDTO> updateTarea(@RequestBody TareaDTO tareaDTO){
        log.info("Request PUT /v1/tareas/update - Actualizando la tarea de ID: {}", tareaDTO.getId());
        TareaDTO updatedTareaDTO = tareaService.saveTarea(tareaDTO);
        return ResponseEntity.ok(updatedTareaDTO);
    }

    @Operation(
            summary = "Eliminar objeto tarea por id",
            description = "Eliminar objeto tarea por id pasado como argumento",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Tarea eliminada con éxito"),
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Tarea> deleteTarea(@PathVariable Long id){
        log.info("Request DELETE /v1/tareas/{} - Eliminando tarea por id", id);
        tareaService.deleteTarea(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Regresa tarea por id.",
            description = "Obtiene un objeto Tarea por un id especifico. La respuesta es un objeto Tarea.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Petición exitosa"),
                    @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<TareaDTO> getTarea(@PathVariable Long id) {
        log.info("Request GET /v1/tareas/{} - Buscando tarea por id", id);
        TareaDTO tareaDTO = tareaService.getTarea(id);
        return ResponseEntity.ok(tareaDTO);
    }

}
