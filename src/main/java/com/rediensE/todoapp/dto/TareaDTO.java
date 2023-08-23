    package com.rediense.todoapp.dto;

    import com.rediense.todoapp.utils.EstadoTarea;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    public class TareaDTO {
        private Long id;
        private String titulo;
        private String descripcion;
        private boolean favorito;
        private EstadoTarea estado;
    }
